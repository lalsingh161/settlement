package org.mifosplatform.billing.mediasettlement.service;

import java.util.ArrayList;
import java.util.List;

import org.mifosplatform.billing.mediasettlement.data.InteractiveDetailsData;
import org.mifosplatform.billing.mediasettlement.data.OperatorStageDetailData;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveDetails;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveHeader;
import org.springframework.stereotype.Service;

@Service
public class InteractiveHeaderHandler {

	
	List<InteractiveHeader> header = new ArrayList<InteractiveHeader>();
	private List<OperatorStageDetailData> rawData;
	
	
	public InteractiveHeaderHandler() {
		
	}
	
	public InteractiveHeaderHandler(List<OperatorStageDetailData> rawData) {
		this.rawData = rawData;
	}

	public InteractiveHeader fromJson(
			List<OperatorStageDetailData> rawData) {
		
		for(OperatorStageDetailData d:rawData){
			InteractiveHeader hData = new InteractiveHeader();
			//hData.setClientId(clientId);
		}
		
		
		return null;
	}

	

	public List<InteractiveHeader> getData() {

		for(OperatorStageDetailData d:this.rawData){
			InteractiveHeader hData  = getCurrentHeader();
			if(alreadyExist(hData)){
				InteractiveHeader h = getCurrentHeader();
				add(h,d.getDetailData());
			}else{
				hData = new InteractiveHeader(d.getHeaderData().getClientId(), d.getHeaderData().getClientCode(), d.getHeaderData().getBusinessLineL(), null,d.getHeaderData().getDataUploadedDate(),d.getHeaderData().getActivityMonth());
				add(hData, d.getDetailData());
				header.add(hData);
			}
			
		}
		
		return header;
	}

	private void add(InteractiveHeader h, InteractiveDetailsData detailData) {
		h.add(new InteractiveDetails(detailData.getPlaySource(), detailData.getContentName(), detailData.getContentProvider(), detailData.getChannelName(), detailData.getServiceName(), detailData.getEndUserPrice(), detailData.getDownloadsL(), detailData.getGrossRevenue(), detailData.getMediaCategory()));
		
	}

	private InteractiveHeader getCurrentHeader() {
		if(header.isEmpty())
			return null;
		return header.get(header.size()-1);
	}

	private boolean alreadyExist(InteractiveHeader hData) {
		if(null == hData)
			return false;
		if(!header.isEmpty()){
			for(InteractiveHeader h:header){
				if(h.getClientId().equals(hData.getClientId()) || h.getBusinessLine().equals(hData.getBusinessLine()) || h.getActivityMonth().equalsIgnoreCase(hData.getActivityMonth()))
					return true;
			}
		}
		return false;
	}

	

	
	
	
	
}
