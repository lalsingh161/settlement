package org.mifosplatform.billing.eventorder.service;

import java.math.BigDecimal;
import java.util.List;

import org.mifosplatform.billing.clientbalance.data.ClientBalanceData;
import org.mifosplatform.billing.clientbalance.service.ClientBalanceReadPlatformService;
import org.mifosplatform.billing.eventmaster.domain.EventDetails;
import org.mifosplatform.billing.eventmaster.domain.EventDetailsRepository;
import org.mifosplatform.billing.eventmaster.domain.EventMaster;
import org.mifosplatform.billing.eventmaster.domain.EventMasterRepository;
import org.mifosplatform.billing.eventorder.domain.EventOrder;
import org.mifosplatform.billing.eventorder.domain.EventOrderRepository;
import org.mifosplatform.billing.eventorder.domain.EventOrderdetials;
import org.mifosplatform.billing.eventorder.exception.InsufficientAmountException;
import org.mifosplatform.billing.eventpricing.domain.EventPricing;
import org.mifosplatform.billing.eventpricing.domain.EventPricingRepository;
import org.mifosplatform.billing.media.domain.MediaAsset;
import org.mifosplatform.billing.media.exceptions.NoEventMasterFoundException;
import org.mifosplatform.billing.media.exceptions.NoMoviesFoundException;
import org.mifosplatform.billing.mediadetails.domain.MediaAssetRepository;
import org.mifosplatform.billing.mediadetails.domain.MediaassetLocation;
import org.mifosplatform.billing.mediadetails.exception.NoMediaDeviceFoundException;
import org.mifosplatform.billing.mediadevice.data.MediaDeviceData;
import org.mifosplatform.billing.mediadevice.service.MediaDeviceReadPlatformService;
import org.mifosplatform.billing.onetimesale.data.OneTimeSaleData;
import org.mifosplatform.billing.onetimesale.serialization.EventOrderCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.onetimesale.service.InvoiceOneTimeSale;
import org.mifosplatform.billing.onetimesale.service.OneTimeSaleReadPlatformService;
import org.mifosplatform.billing.transactionhistory.service.TransactionHistoryWritePlatformService;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventOrderWriteplatformServiceImpl implements
		EventOrderWriteplatformService {

	private final PlatformSecurityContext context;
	private final EventOrderRepository eventOrderRepository;
	private final EventOrderCommandFromApiJsonDeserializer apiJsonDeserializer;
	private final InvoiceOneTimeSale invoiceOneTimeSale;
	private final EventMasterRepository eventMasterRepository;
	private final MediaAssetRepository mediaAssetRepository;
	private final EventOrderReadplatformServie eventOrderReadplatformServie;
	private final MediaDeviceReadPlatformService deviceReadPlatformService;
	private final ClientBalanceReadPlatformService balanceReadPlatformService;
	private final EventDetailsRepository eventDetailsRepository;
	private final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService;
	private final EventPricingRepository eventPricingRepository;

	@Autowired
	public EventOrderWriteplatformServiceImpl(
			final PlatformSecurityContext context,
			final EventOrderRepository eventOrderRepository,
			final EventOrderCommandFromApiJsonDeserializer apiJsonDeserializer,
			final EventOrderReadplatformServie eventOrderReadplatformServie,
			final InvoiceOneTimeSale invoiceOneTimeSale,
			final OneTimeSaleReadPlatformService oneTimeSaleReadPlatformService,
			final EventMasterRepository eventMasterRepository,
			final MediaAssetRepository mediaAssetRepository,
			final MediaDeviceReadPlatformService deviceReadPlatformService,
			final ClientBalanceReadPlatformService balanceReadPlatformService,
			final EventDetailsRepository eventDetailsRepository,
			final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService,
			final EventPricingRepository eventPricingRepository) {
		
		this.context = context;
		this.eventOrderRepository = eventOrderRepository;
		this.apiJsonDeserializer = apiJsonDeserializer;
		this.invoiceOneTimeSale = invoiceOneTimeSale;
		this.eventMasterRepository = eventMasterRepository;
		this.mediaAssetRepository = mediaAssetRepository;
		this.eventOrderReadplatformServie = eventOrderReadplatformServie;
		this.deviceReadPlatformService = deviceReadPlatformService;
		this.balanceReadPlatformService = balanceReadPlatformService;
		this.eventDetailsRepository=eventDetailsRepository;
		this.transactionHistoryWritePlatformService = transactionHistoryWritePlatformService;
		this.eventPricingRepository = eventPricingRepository;

	}

	private void handleCodeDataIntegrityIssues(JsonCommand command,
			DataIntegrityViolationException dve) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public CommandProcessingResult createEventOrder(JsonCommand command) {
		try {
			this.context.authenticatedUser();
			this.apiJsonDeserializer.validateForCreate(command.json(),command);
			
			
			
			
			
			
			final Long cId = command.longValueOfParameterNamed("cId");
			
			/*
			 * adding this line of code because of import functionality, where 
			 * we are not passing client type, so we have to retrive client id through its client id
			 * */
			final Long clientType = eventOrderReadplatformServie.getClientType(cId);
			
					
					/*final String deviceId = command.stringValueOfParameterNamed("deviceId");
			MediaDeviceData deviceData = this.deviceReadPlatformService.retrieveDeviceDetails(deviceId);
			if (deviceData == null) {
				throw new NoMediaDeviceFoundException();
			}*/
			
			
		/*	//Check Client Custome Validation
			boolean isCheck=this.eventOrderReadplatformServie.CheckClientCustomalidation(deviceData.getClientId());
			if(!isCheck){
				throw new  CustomValidationException();
			}*/
			
			
			final String formatType = command.stringValueOfParameterNamed("formatType");
			final String optType = command.stringValueOfParameterNamed("optType");
			
			final BigDecimal uP = command.bigDecimalValueOfParameterNamed("updatedPrice"); 
			final Double updatedPrice = uP.doubleValue(); 
			
			EventMaster eventMaster = null;
			Long eventId = null;
			if(command.parameterExists("eventId")){
				eventId = command.longValueOfParameterNamed("eventId");
				eventMaster = this.eventMasterRepository.findOne(eventId);
			}else if(command.parameterExists("eventName")){
				final String  eventName = command.stringValueOfParameterNamed("eventName");
				try{
				eventId = eventOrderReadplatformServie.getEventId(eventName);
				eventMaster = this.eventMasterRepository.findOne(eventId);
				}catch(EmptyResultDataAccessException de){
					throw new NoEventMasterFoundException();
				}
			}
			
			
			if(eventMaster == null){
				throw new NoEventMasterFoundException();
			}
			
			List<EventDetails> eventDetails = eventMaster.getEventDetails();
			
			EventOrder eventOrder = EventOrder.fromJson(command,eventMaster, null,clientType,eventId);
			
			if(eventOrder.getBookedPrice() != updatedPrice){
				eventOrder.setBookedPrice(updatedPrice);
			}
	
			for(EventDetails detail:eventDetails){
				
				EventDetails eventDetail = this.eventDetailsRepository.findOne(detail.getId());
				MediaAsset mediaAsset = this.mediaAssetRepository.findOne(eventDetail.getMediaId());
				List<MediaassetLocation> mediaassetLocations = mediaAsset.getMediaassetLocations();
				String movieLink = "";
				
				for (MediaassetLocation location : mediaassetLocations) {
					if (location.getFormatType().equalsIgnoreCase(formatType)) {
						movieLink = location.getLocation();
					}
				}
				      EventOrderdetials eventOrderdetials=new EventOrderdetials(eventDetail,movieLink,formatType,optType);
				      eventOrder.addEventOrderDetails(eventOrderdetials);
				
				    if (movieLink.isEmpty()) {
				    	if(mediaAsset.getCategoryId() != 999991)/*999991 means it is a game */
				    		throw new NoMoviesFoundException();
				    }
			}
			
			//eventOrder.setBookedPrice(updatedPrice.doubleValue());
			
			boolean hasSufficientMoney = this.checkClientBalance(eventOrder.getBookedPrice(), cId);
			if (!hasSufficientMoney) {
				//throw new InsufficientAmountException();
			}

			this.eventOrderRepository.save(eventOrder);

			/*
			 * Based on shiva sir request, i have kept this comment to stop updating invoice
			 * */
			/*List<OneTimeSaleData> oneTimeSaleDatas = eventOrderReadplatformServie.retrieveEventOrderData(eventOrder.getClientId());
			for (OneTimeSaleData oneTimeSaleData : oneTimeSaleDatas) {
				this.invoiceOneTimeSale.invoiceOneTimeSale(eventOrder.getClientId(), oneTimeSaleData);

				this.updateOneTimeSale(oneTimeSaleData);
			}*/
            
			transactionHistoryWritePlatformService.saveTransactionHistory(eventOrder.getClientId(), "Event Order", eventOrder.getEventBookedDate(),"CancelFlag:"+eventOrder.getCancelFlag(),
					"bookedPrice:"+eventOrder.getBookedPrice(),"EventValidTillDate:"+eventOrder.getEventValidtill(),"EventId:"+eventOrder.getEventId(),"EventOrderID:"+eventOrder.getId());
			
			return new CommandProcessingResultBuilder().withEntityId(eventId).build();
		} catch (DataIntegrityViolationException dve) {
			handleCodeDataIntegrityIssues(command, dve);
			return new CommandProcessingResult(Long.valueOf(-1));
		}
	}

	
	@Override
	public CommandProcessingResult updateEventOrderPrice(JsonCommand command) {
		
		Long id = eventOrderReadplatformServie.getCurrentRow(command.stringValueOfParameterNamed("formatType"), command.stringValueOfParameterNamed("optType"), command.longValueOfParameterNamed("clientId"),command.longValueOfParameterNamed("eventId"));
		EventPricing eventPricing = eventPricingRepository.findOne(id);
		eventPricing.setPrice(Double.valueOf(command.stringValueOfParameterNamed("price")));
		eventPricingRepository.save(eventPricing);
		return new CommandProcessingResultBuilder().withResourceIdAsString(eventPricing.getPrice().toString()).build();
	}
	
	public boolean checkClientBalance(Double bookedPrice, Long clientId) {
		  boolean isBalanceAvailable = false;
		  ClientBalanceData clientBalance=this.balanceReadPlatformService.retrieveBalance(clientId);
		  BigDecimal eventPrice = new BigDecimal(bookedPrice);
		  if(clientBalance!=null){
			  BigDecimal resultantBalance = clientBalance.getBalanceAmount().add(eventPrice);
			  if(resultantBalance.compareTo(BigDecimal.ZERO) == -1 || resultantBalance.compareTo(BigDecimal.ZERO) == 0){
			  isBalanceAvailable = true;   
			  }else {
				  isBalanceAvailable = false;
			  }
		  }
		  return isBalanceAvailable;
	}

	public void updateOneTimeSale(OneTimeSaleData oneTimeSaleData) {
		EventOrder oneTimeSale = eventOrderRepository.findOne(oneTimeSaleData.getId());
		oneTimeSale.setInvoiced();
		eventOrderRepository.save(oneTimeSale);

	

	}
}