package org.mifosplatform.billing.mediasettlement.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.mediasettlement.data.DisbursementData;
import org.mifosplatform.billing.mediasettlement.data.DisbursementsData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveDetailsData;
import org.mifosplatform.billing.mediasettlement.data.InteractiveHeaderData;
import org.mifosplatform.billing.mediasettlement.data.MediaCategoryData;
import org.mifosplatform.billing.mediasettlement.data.MediaSettlementCommand;
import org.mifosplatform.billing.mediasettlement.data.OperatorDeductionData;
import org.mifosplatform.billing.mediasettlement.data.PartnerAccountData;
import org.mifosplatform.billing.mediasettlement.data.PartnerAgreementData;
import org.mifosplatform.billing.mediasettlement.data.PartnerGameData;
import org.mifosplatform.billing.mediasettlement.data.PartnerGameDetailsData;
import org.mifosplatform.billing.mediasettlement.data.RevenueSettlementSequenceData;
import org.mifosplatform.billing.mediasettlement.data.RevenueShareData;
import org.mifosplatform.billing.mediasettlement.domain.AccountPartnerJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.InteractiveHeaderJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.RevenueOEMSettlementJpaRepository;
import org.mifosplatform.billing.mediasettlement.domain.RevenueSettlementJpaRepository;
import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.documentmanagement.exception.DocumentNotFoundException;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.organisation.monetary.data.CurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class MediaSettlementReadPlatformServiceImp implements
		MediaSettlementReadPlatformService {

	final private PlatformSecurityContext context;
	final private AccountPartnerJpaRepository accountPartnerJpaRepository;
	final private JdbcTemplate jdbcTemplate;
	final private RevenueOEMSettlementJpaRepository revenueOEMSettlementJpaRepository;
	final private RevenueSettlementJpaRepository revenueSettlementJpaRepository;
	final private InteractiveHeaderJpaRepository interactiveHeaderJpaRepository;

	@Autowired
	public MediaSettlementReadPlatformServiceImp(
			final PlatformSecurityContext context,
			final AccountPartnerJpaRepository accountPartnerJpaRepository,
			final TenantAwareRoutingDataSource dataSource,
			final RevenueSettlementJpaRepository revenueSettlementJpaRepository,
			final RevenueOEMSettlementJpaRepository revenueOEMSettlementJpaRepository,
			InteractiveHeaderJpaRepository interactiveHeaderJpaRepository) {
		this.context = context;
		this.accountPartnerJpaRepository = accountPartnerJpaRepository;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.revenueOEMSettlementJpaRepository = revenueOEMSettlementJpaRepository;
		this.revenueSettlementJpaRepository = revenueSettlementJpaRepository;
		this.interactiveHeaderJpaRepository = interactiveHeaderJpaRepository;
	}

	@Override
	public List<PartnerAccountData> retrieveAllAccountPartnerDetails() {
		context.authenticatedUser();
		/*
		 * final String sql =
		 * "select pt.id as id, (select code_value from m_code_value where id = pt.partner_type) as partnerType,(select code from m_currency where id = pt.currency_id) as currencyCode, (select code_value from m_code_value where id = pt.media_category) as mediaCategory, pt.partner_name as partnerName, pt.partner_address as partnerAddress from bp_account pt where pt.is_deleted='N' order by pt.id asc"
		 * ;
		 */
		/*
		 * final String sql =
		 * "select pt.id as id,pt.contact_num as contactNum,pt.official_email_id as emailId,pt.external_id as externalId,pt.currency_id as currencyId, pt.partner_name as partnerName, pt.partner_address as partnerAddress from bp_account pt order by pt.id asc"
		 * ;
		 */
		final String sql = "select pt.id as id,pt.contact_num as contactNum,pt.official_email_id as emailId,pt.external_id as externalId,(select code from m_currency where id=pt.currency_id) as currencyCode, pt.partner_name as partnerName, pt.partner_address as partnerAddress from bp_account pt order by pt.id asc";
		PartnerAccountMapper mapper = new PartnerAccountMapper();
		return jdbcTemplate.query(sql, mapper);
	}

	private static final class PartnerAccountMapper implements
			RowMapper<PartnerAccountData> {
		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String partnerName = rs.getString("partnerName");
			final String currencyCode = rs.getString("currencyCode");
			final String partnerAddress = rs.getString("partnerAddress");
			final Long externalId = rs.getLong("externalId");
			final String contactNum = rs.getString("contactNum");
			final String emailId = rs.getString("emailId");
			return new PartnerAccountData(id, partnerName, partnerAddress,
					null, currencyCode, externalId, contactNum, emailId);
		}
	}

	private static final class EditPartnerAccountMapper implements
			RowMapper<PartnerAccountData> {
		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String partnerName = rs.getString("partnerName");
			final Long currencyId = rs.getLong("currencyId");
			final String partnerAddress = rs.getString("partnerAddress");
			final Long externalId = rs.getLong("externalId");
			final String contactNum = rs.getString("contactNum");
			final String emailId = rs.getString("emailId");
			return new PartnerAccountData(id, partnerName, partnerAddress,
					currencyId, null, externalId, contactNum, emailId);
		}
	}

	@Override
	public PartnerAccountData retrieveAllAccountPartnerDetail(Long id) {
		context.authenticatedUser();
		/*
		 * final String sql =
		 * "select pt.id as id, pt.partner_type as partnerType, pt.currency_id as currencyCode, pt.media_category as mediaCategory, pt.partner_name as partnerName, pt.partner_address as partnerAddress from bp_account pt where pt.id = ? and pt.is_deleted='N'"
		 * ;
		 */
		final String sql = "select pt.id as id,pt.contact_num as contactNum,pt.official_email_id as emailId,pt.external_id as externalId,pt.currency_id as currencyId, pt.partner_name as partnerName, pt.partner_address as partnerAddress from bp_account pt where pt.id = ?";
		EditPartnerAccountMapper mapper = new EditPartnerAccountMapper();
		return jdbcTemplate.queryForObject(sql, mapper, new Object[] { id });
	}

	@Override
	public List<PartnerAgreementData> retrievePartnerAgreementDetails() {
		
	final String sql = "select a.id as id,(select partner_name from bp_account where id = a.partner_account_id and is_deleted='N') as partnerName ,(select code_value from m_code_value where id = a.agreement_type) as agreementType, (select code_value from m_code_value where id = a.agreement_category) as agreementCategory,(select code_value from m_code_value where id = a.settle_source) as settlementSource, (select code_value from m_code_value where id = a.royalty_type) as royaltyType, a.start_date as startDate, a.end_date as endDate, a.agmt_location as agmtLocation,a.filename as fileName from bp_agreement a where a.is_deleted='N' order by id asc";
		PartnerAgreementMapper mapper = new PartnerAgreementMapper();
		return jdbcTemplate.query(sql, mapper);
	}

	private final static class PartnerAgreementMapper implements
			RowMapper<PartnerAgreementData> {
		@Override
		public PartnerAgreementData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String partnerName = rs.getString("partnerName");
			final String agreementType = rs.getString("agreementType");
			final String agreementCategory = rs.getString("agreementCategory");
			final String royaltyType = rs.getString("royaltyType");
			final LocalDate startDate = JdbcSupport.getLocalDate(rs,"startDate");
			final LocalDate endDate = JdbcSupport.getLocalDate(rs, "endDate");
			final String agmtLocation = rs.getString("agmtLocation");
			final String settlementSource = rs.getString("settlementSource");
			final String fileName = rs.getString("fileName");
			return new PartnerAgreementData(id, partnerName, agreementType,
					agreementCategory, royaltyType, startDate, endDate,
					agmtLocation, settlementSource, fileName);
		}
	}

	@Override
	public Collection<PartnerAccountData> retrievePartnerNames() {
		final String sql = "select pa.id as id, pa.partner_name as partnerName from bp_account pa where pa.is_deleted = 'N' order by pa.partner_name";
		PartnerNames names = new PartnerNames();
		return jdbcTemplate.query(sql, names);
	}

	private final static class PartnerNames implements
			RowMapper<PartnerAccountData> {
		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String partnerName = rs.getString("partnerName");
			return new PartnerAccountData(id, partnerName);
		}
	}

	@Override
	public MediaCategoryData retrieveMediaCategory(Long partnerId) {
		String sql = "select a.id as id, a.media_category as mediaCategoryId, m.code_value as mediaCategory from bp_account a inner join m_code_value m on m.id=a.media_category where a.id = ? and a.is_deleted='N'";
		MediaCategoryMapper mapper = new MediaCategoryMapper();
		return jdbcTemplate.queryForObject(sql, mapper,
				new Object[] { partnerId });
	}

	private static final class MediaCategoryMapper implements
			RowMapper<MediaCategoryData> {

		@Override
		public MediaCategoryData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String mediaCategory = rs.getString("mediaCategory");
			final Long mediaCategoryId = rs.getLong("mediaCategoryId");
			return new MediaCategoryData(id, mediaCategory, mediaCategoryId);
		}
	}

	@Override
	public MediaCategoryData retrivePartnerType(Long partnerId) {
		final String sql = "select a.id as id,a.partner_type as partnerTypeId, m.code_value as partnerType from bp_account a inner join m_code_value m on m.id=a.partner_type where a.id = ? and a.is_deleted='N'";
		PartnerTypeMapper mapper = new PartnerTypeMapper();
		return jdbcTemplate.queryForObject(sql, mapper,
				new Object[] { partnerId });
	}

	private static final class PartnerTypeMapper implements
			RowMapper<MediaCategoryData> {

		@Override
		public MediaCategoryData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String partnerType = rs.getString("partnerType");
			final Long partnerTypeId = rs.getLong("partnerTypeId");
			return new MediaCategoryData(id, partnerTypeId, partnerType);
		}
	}

	@Override
	public Collection<PartnerGameDetailsData> retriveAllPartnerGameDetails() {
		final String sql = "select g.id as id, acc.partner_name as partnerName, mcv.code_value as playSource, g.royalty_value as royaltyValue, g.royalty_sequence as royaltySequence from bp_games g inner join bp_account acc on acc.id=g.partner_id inner join m_code_value mcv on mcv.id=g.play_source where acc.is_deleted='N' order by g.id";
		PartnerGameDetailsMapper mapper = new PartnerGameDetailsMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] {});
	}

	/*
	 * select g.id as id, acc.partner_name as partnerName, mcv.code_value as
	 * playSource, g.royalty_value as royaltyValue, g.royalty_sequence as
	 * royaltySequence, gd.serno as serialNumber, gd.game as game, gd.game_dt as
	 * gameDate, gd.gameplaysource_code as gamePlaySource, gd.game_price as
	 * gamePrice, gd.royalty_per as royaltyPercentage from bp_games g inner join
	 * bp_account acc on acc.id=g.partner_id inner join m_code_value mcv on
	 * mcv.id=g.play_source inner join bp_game_details gd on g.id = gd.game_id
	 * where g.id=?
	 */

	private static final class PartnerGameDetailsMapper implements
			RowMapper<PartnerGameDetailsData> {
		@Override
		public PartnerGameDetailsData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String partnerName = rs.getString("partnerName");
			final String playSource = rs.getString("playSource");
			final BigDecimal royaltyValue = rs.getBigDecimal("royaltyValue");
			final Long royaltySequence = rs.getLong("royaltySequence");
			return new PartnerGameDetailsData(id, partnerName, playSource,
					royaltyValue, royaltySequence);
		}
	}

	@Override
	public PartnerGameDetailsData retrivePartnerGameDetails(Long id) {
		final String sql = "select g.id as id, acc.id as partnerName, (select code_value from m_code_value where id = acc.media_category) as mediaCategory,(select code_value from m_code_value where id = acc.partner_type) as partnerType, g.play_source as playSource, g.royalty_value as royaltyValue, g.royalty_sequence as royaltySequence from bp_games g inner join bp_account acc ON acc.id = g.partner_id where g.id = ? and acc.is_deleted='N' order by g.id";/*
																																																																																																																		 * "select g.id as id, g.partner_id as partnerName, acc.media_category as mediaCategory, acc.partner_type as partnerType, g.play_source as playSource, g.royalty_value as royaltyValue, g.royalty_sequence as royaltySequence from bp_games g inner join bp_account acc ON acc.id = g.partner_id where g.id = ? order by g.id"
																																																																																																																		 * ;
																																																																																																																		 */
		EditPartnerGameDetailsMapper mapper = new EditPartnerGameDetailsMapper();
		return jdbcTemplate.queryForObject(sql, mapper, new Object[] { id });
	}

	private static final class EditPartnerGameDetailsMapper implements
			RowMapper<PartnerGameDetailsData> {
		@Override
		public PartnerGameDetailsData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final Long partnerName = rs.getLong("partnerName");
			final Long playSource = rs.getLong("playSource");
			final BigDecimal royaltyValue = rs.getBigDecimal("royaltyValue");
			final Long royaltySequence = rs.getLong("royaltySequence");
			final String mediaCategory = rs.getString("mediaCategory");
			final String partnerType = rs.getString("partnerType");
			return new PartnerGameDetailsData(id, partnerName, playSource,
					royaltyValue, royaltySequence, mediaCategory, partnerType);
		}
	}

	@Override
	public Collection<PartnerGameDetailsData> retriveChannelGameDetails(Long id) {
		final String sql = "select gd.id as id,gd.serno as serialNumber, gd.media_id as game, gd.game_dt as gameDate, gd.gameplaysource_code as gamePlaySource, gd.game_price as gamePrice, gd.royalty_per as royaltyPercentage from bp_game_details gd where gd.game_id=?";
		EditChannelPartnerDetailsMapper mapper = new EditChannelPartnerDetailsMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { id });
	}

	private static final class EditChannelPartnerDetailsMapper implements
			RowMapper<PartnerGameDetailsData> {
		@Override
		public PartnerGameDetailsData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final Long serialNumber = rs.getLong("serialNumber");
			final Long game = rs.getLong("game");
			final LocalDate gameDate = JdbcSupport.getLocalDate(rs, "gameDate");
			final Long gamePlaySource = rs.getLong("gamePlaySource");
			final BigDecimal gamePrice = rs.getBigDecimal("gamePrice");
			final BigDecimal royaltyPercentage = rs
					.getBigDecimal("royaltyPercentage");
			return new PartnerGameDetailsData(serialNumber, game, gameDate,
					gamePlaySource, gamePrice, royaltyPercentage, id);
		}
	}

	@Override
	public Collection<RevenueSettlementSequenceData> retriveRevenueSettlementSequenceData(
			Long categoryId) {
		final String sql = "select pv.id as id, pv.partner_type as partnerTypeId, pv.partnertype as partnerType, pv.mediacategory as mediaCategory, pv.media_category as mediaCategoryId, pv.partner_name as partnerName, pv.agreement_category as agreementCategory, pv.settle_source as settlementSource, pv.play_source as playSourceId, pv.playsource as playSource, pv.royalty_value as royaltyValueFromGame, pv.royalty_sequence as royaltySequence, pv.royalty_per as royaltyPercentage from bp_partner_vw pv where pv.media_category = ?";
		revenueOEMSettlementJpaRepository.deleteAll();
		revenueSettlementJpaRepository.deleteAll();
		RevenueSettlementSequenceMapper mapper = new RevenueSettlementSequenceMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { categoryId });
	}

	/*
	 * @Override public RevenueSettlementSequenceData getGamePrice(String
	 * stringValueOfParameterNamed) { String sql =
	 * "select game_price as price from bp_game_details where game = ? limit 1";
	 * GamePriceMapper mapper = new GamePriceMapper(); return
	 * jdbcTemplate.queryForObject(sql, mapper, new
	 * Object[]{stringValueOfParameterNamed}); }
	 */

	@Override
	public RevenueSettlementSequenceData getGamePrice(Long gameId) {
		String sql = "select game_price as price from bp_game_details where media_id = ? limit 1";
		GamePriceMapper mapper = new GamePriceMapper();
		return jdbcTemplate
				.queryForObject(sql, mapper, new Object[] { gameId });
	}

	private static final class GamePriceMapper implements
			RowMapper<RevenueSettlementSequenceData> {
		@Override
		public RevenueSettlementSequenceData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final BigDecimal price = rs.getBigDecimal("price");
			return new RevenueSettlementSequenceData(price);
		}
	}

	private static final class RevenueSettlementSequenceMapper implements
			RowMapper<RevenueSettlementSequenceData> {

		@Override
		public RevenueSettlementSequenceData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final Long partnerTypeId = rs.getLong("partnerTypeId");
			final String partnerType = rs.getString("partnerType");
			final String mediaCategory = rs.getString("mediaCategory");
			final Long mediaCategoryId = rs.getLong("mediaCategoryId");
			final String partnerName = rs.getString("partnerName");
			final Long agreementCategory = rs.getLong("agreementCategory");
			final Long settlementSource = rs.getLong("settlementSource");
			final Long playSourceId = rs.getLong("playSourceId");
			final String playSource = rs.getString("playSource");
			final BigDecimal royaltyValueForGame = rs
					.getBigDecimal("royaltyValueFromGame");
			final Long royaltySequence = rs.getLong("royaltySequence");
			/* final Long gameId = rs.getLong("gameId"); */
			/* final String game = rs.getString("game"); */
			/* final BigDecimal gamePrice = rs.getBigDecimal("gamePrice"); */
			final BigDecimal royaltyPercentage = rs
					.getBigDecimal("royaltyPercentage");
			/* final BigDecimal royalty = rs.getBigDecimal("royalty"); */
			/* final Long royaltyType = rs.getLong("royaltyType"); */
			return new RevenueSettlementSequenceData(id, partnerTypeId,
					partnerType, mediaCategory, mediaCategoryId, partnerName,
					agreementCategory, settlementSource, playSourceId,
					playSource, royaltyValueForGame, royaltySequence,
					null/* gameId */,/* game */null, royaltyPercentage,/* royalty */
					null,/* gamePrice */null);
		}
	}

	public MediaSettlementCommand retrieveDocument(Long documentId) {
		// TODO Auto-generated method stub

		try {
			this.context.authenticatedUser();

			// TODO verify if the entities are valid and a
			// user has data
			// scope for the particular entities
			final MediaSettlementCommandMapper mapper = new MediaSettlementCommandMapper(
					false);
			final String sql = "select DISTINCT " + mapper.schema() + " and g.id=? ";
			return this.jdbcTemplate.queryForObject(sql, mapper,
					new Object[] { documentId });
		} catch (final EmptyResultDataAccessException e) {
			throw new DocumentNotFoundException("File Not Found", documentId,
					documentId);
		}
	}

	private static final class MediaSettlementCommandMapper implements
			RowMapper<MediaSettlementCommand> {

		private final boolean hideLocation;

		public MediaSettlementCommandMapper(final boolean hideLocation) {
			this.hideLocation = hideLocation;
		}

		public String schema() {
			/*
			 * return
			 * " g.agreement_type as agreementType,g.settle_source as settlementSource ,g.agreement_category as agreementCategory,g.royalty_type as royaltyType,g.play_source as playSource,g.royalty_value as royaltyValue,g.royalty_sequence as royaltySequence , g.start_date as startDate,g.end_date as endDate, a.media_category as mediaCategory,a.partner_type as partnerType,a.partner_name as partnerName, g.partner_account_id as partnerAccountId from bp_account a,bp_agreement g where g.partner_account_id=a.id"
			 * ;
			 * " g.agreement_type as agreementType,g.settle_source as settlementSource ,g.agreement_category as agreementCategory,g.royalty_type as royaltyType, g.start_date as startDate,g.end_date as endDate, a.media_category as mediaCategory,a.partner_type as partnerType,a.partner_name as partnerName, g.partner_account_id as partnerAccountId from bp_account a,bp_agreement g where g.partner_account_id=a.id and a.is_deleted='N'"
			 */

			return " g.id as id, g.agreement_type as agreementType,g.settle_source as settlementSource ,g.agreement_category as agreementCategory,g.royalty_type as royaltyType,g.mg_amount as mgAmount , g.start_date as startDate,g.end_date as endDate, a.partner_name as partnerName, g.partner_account_id as partnerAccountId,d.partner_type as partnerType from bp_account a,bp_agreement g,bp_agreement_dtl d where g.partner_account_id=a.id AND d.partner_account_id = a.id  ";


		}

		@Override
		public MediaSettlementCommand mapRow(final ResultSet rs,
				@SuppressWarnings("unused") final int rowNum)
				throws SQLException {


			final Long id = JdbcSupport.getLong(rs, "id");
			
			final Long partnerType = JdbcSupport.getLong(rs, "partnerType");

			//final Long mediaCategory = JdbcSupport.getLong(rs, "mediaCategory");

			final String partnerName = rs.getString("partnerName");

			final Long partnerAccountId = JdbcSupport.getLong(rs,
					"partnerAccountId");

			final Long agreementType = JdbcSupport.getLong(rs, "agreementType");

			final Long agreementCategory = JdbcSupport.getLong(rs,
					"agreementCategory");

			final Long royaltyType = JdbcSupport.getLong(rs, "royaltyType");

			final LocalDate startDate = JdbcSupport.getLocalDate(rs,
					"startDate");

			final LocalDate endDate = JdbcSupport.getLocalDate(rs, "endDate");

			final Long settlementSource = JdbcSupport.getLong(rs,
					"settlementSource");

			//final Long playSource = JdbcSupport.getLong(rs, "playSource");
			//final BigDecimal royaltyShare = JdbcSupport
			//		.getBigDecimalDefaultToNullIfZero(rs, "royaltyShare");
			//final Long royaltySequence = JdbcSupport.getLong(rs,
			//		"royaltySequence");

			final BigDecimal mgAmount = JdbcSupport
					.getBigDecimalDefaultToZeroIfNull(rs, "mgAmount");
			
			//final Long status = JdbcSupport	.getLong(rs, "status");

			return new MediaSettlementCommand(id,partnerName, agreementType,
					agreementCategory, royaltyType, startDate, endDate,
					 partnerAccountId,settlementSource,	 mgAmount,partnerType);
		}

	}

	@Override
	public Collection<PartnerGameData> retriveAllContentProviderGames() {
		final String sql = "select ma.id as mediaAssetId, ma.title as game from b_media_asset ma where ma.media_category='Games'";
		GameNameMapper mapper = new GameNameMapper();
		return jdbcTemplate.query(sql, mapper);
	}

	private static final class GameNameMapper implements
			RowMapper<PartnerGameData> {

		public PartnerGameData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long mediaAssetId = rs.getLong("mediaAssetId");
			final String game = rs.getString("game");
			return new PartnerGameData(mediaAssetId, game);
		};
	}

	@Override
	public PartnerAccountData retrieveContentProviderPartnerId(
			String mediaCategory) {
		/*final String sql = "select pa.id as partnerId ,mcv.code_value as partnerType from bp_account pa inner join m_code_value mcv on mcv.id = pa.partner_type where pa.id = ? and pa.is_deleted='N'";*/
		final String sql = "select pa.partner_account_id as partnerId, mcv.code_value as partnerType, pad.partner_type as pType from bp_agreement pa inner join bp_agreement_dtl pad on pa.id=pad.agmt_id inner join m_code_value mcv on mcv.id=pad.partner_type inner join bp_account a on a.id=pa.partner_account_id where pa.partner_account_id=? and a.is_deleted='N' and pa.is_deleted='N'";
		RetrivePartnerTypeMapper mapper = new RetrivePartnerTypeMapper();
		return jdbcTemplate.queryForObject(sql, mapper,
				new Object[] { mediaCategory });
	}

	private static final class RetrivePartnerTypeMapper implements
			RowMapper<PartnerAccountData> {
		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long partnerId = rs.getLong("partnerId");
			final String partnerType = rs.getString("partnerType");
			return new PartnerAccountData(partnerId, partnerType);
		}
	}

	@Override
	public String getPartnerType(Long partnerType) {
		String sql = "select code_value as partnerType from m_code_value where id = ?";
		PartnerTypeContentMapper mapper = new PartnerTypeContentMapper();
		return jdbcTemplate.queryForObject(sql, mapper, new Object[] {});
	}

	private final static class PartnerTypeContentMapper implements
			RowMapper<String> {
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			final String partnerType = rs.getString("partnerType");
			return partnerType;
		}
	}

	@Override
	public Collection<DisbursementData> retriveDisbursementData() {

		return null;
	}

	@Override
	public Long getPartnerType(String partnerType) {
		final String sql = "select id as channelPartnerId from m_code_value where code_value= ?";
		return jdbcTemplate.queryForLong(sql, new Object[] { partnerType });
	}

	@Override
	public Collection<PartnerAccountData> retriveAllChannelPartner() {
		final String sql = "select pa.partner_name as channelName , pa.id as channelPartnerId from bp_account pa inner join bp_ch_map_cp mp on mp.channel_partner_id = pa.id where mp.mapped='N' and mp.is_deleted='N' and pa.is_deleted='N'";
		ChannelPartnerMapper mapper = new ChannelPartnerMapper();
		return jdbcTemplate.query(sql, mapper);
	}

	private final static class ChannelPartnerMapper implements
			RowMapper<PartnerAccountData> {
		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final String channelPartnerName = rs.getString("channelName");
			final Long channelPartnerId = rs.getLong("channelPartnerId");
			return new PartnerAccountData(channelPartnerName, channelPartnerId);
		}
	}

	@Override
	public Long getMapId(Long channelPartnerId) {
		try {
			final String sql = "select id from bp_ch_map_cp where channel_partner_id = ? and is_deleted='N'";
			return jdbcTemplate.queryForLong(sql,
					new Object[] { channelPartnerId });
		} catch (EmptyResultDataAccessException e) {
			throw new PlatformDataIntegrityException(
					"channel.partner.does.not.exist",
					"channel.partner.does.not.exist", "");
		}
	}

	@Override
	public List<PartnerAccountData> getChannelPartnerDetails(Long id) {
		/*
		 * String sql =
		 * "select ac.partner_name as channelPartner, (select code_value from m_code_value where id = ac.partner_type) as channelType, (select code_value from m_code_value where id = ac.media_category) as gameCategory,ac.partner_address as partnerAddress from bp_account ac inner join bp_ch_map_cp m ON ac.id = m.channel_partner_id where m.partner_id = ? and m.is_deleted='N' and ac.is_deleted='N'"
		 * ;
		 */
		String sql = "select ac.id as channelPartnerId, ac.partner_name as channelPartnerName from bp_account ac inner join bp_ch_map_cp m ON ac.id = m.channel_partner_id where m.partner_id=? and m.is_deleted='N' and ac.is_deleted='N'";
		ChannelPartnerDetailsMapper mapper = new ChannelPartnerDetailsMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { id });
	}

	private final static class ChannelPartnerDetailsMapper implements
			RowMapper<PartnerAccountData> {

		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long channelPartnerId = rs.getLong("channelPartnerId");
			final String channelPartnerName = rs
					.getString("channelPartnerName");
			/*
			 * final String channelType = rs.getString("channelType"); final
			 * String gameCategory = rs.getString("gameCategory"); final String
			 * partnerAddress = rs.getString("partnerAddress");
			 */
			return new PartnerAccountData(channelPartnerName, channelPartnerId,
					null, null, null);
		}
	}

	@Override
	public Collection<PartnerGameData> retriveAllContentProviderGames(
			Long partnerId) {
		final String sql = "select ma.id as mediaAssetId, ma.title as game from b_media_asset ma where ma.media_category='Games' and ma.content_provider = ?";
		GameNameMapper mapper = new GameNameMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { partnerId });

	}

	@Override
	public List<DisbursementsData> retrieveAllDisbursementsDataDetails(
			Long month, String partnerName, Long partnerTypeId) {
		context.authenticatedUser();
		final String sql = "SELECT branch as branch,city as city,state as state ,client as client, invoiceId as invoiceId,"
				+ "invoiceAmount as invoiceAmount,invoiceDate as invoiceDate,Media_Category as mediaCategory,partnerTypeId as partnerTypeId,"
				+ "amountSharable as amountSharable,sequence as sequence,playSource as playSource ,partnerName as partnerName,"
				+ "royaltyType as royaltyType,	partnerType as partnerType,commPercent as commPercent,"
				+ "royaltyAmount as royaltyAmount ,netAmount as netAmount FROM  bp_settle_vw s WHERE MONTH(invoiceDate)=? and "
				+ "partnerName=? and partnerTypeId = ?";

		DistributionDataMapper mapper = new DistributionDataMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { month,
				partnerName, partnerTypeId });
	}

	private static final class DistributionDataMapper implements
			RowMapper<DisbursementsData> {
		@Override
		public DisbursementsData mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			final String branch = rs.getString("branch");
			final String city = rs.getString("city");
			final String state = rs.getString("state");
			final String client = rs.getString("client");
			final String invoiceId = rs.getString("invoiceId");
			final BigDecimal invoiceAmount = JdbcSupport
					.getBigDecimalDefaultToZeroIfNull(rs, "invoiceAmount");
			final LocalDate invoiceDate = JdbcSupport.getLocalDate(rs,
					"invoiceDate");
			final Long mediaCategory = rs.getLong("mediaCategory");
			final BigDecimal partnerTypeId = rs.getBigDecimal("partnerTypeId");
			final String amountSharable = rs.getString("amountSharable");
			final Long sequence = rs.getLong("sequence");
			final String playSource = rs.getString("playSource");
			final String partnerName = rs.getString("partnerName");
			final String royaltyType = rs.getString("royaltyType");
			final String partnerType = rs.getString("partnerType");
			final BigDecimal commPercent = JdbcSupport
					.getBigDecimalDefaultToZeroIfNull(rs, "commPercent");
			final BigDecimal royaltyAmount = JdbcSupport
					.getBigDecimalDefaultToZeroIfNull(rs, "royaltyAmount");
			final BigDecimal netAmount = JdbcSupport
					.getBigDecimalDefaultToZeroIfNull(rs, "netAmount");

			return new DisbursementsData(branch, city, state, client,
					invoiceId, invoiceAmount, invoiceDate, mediaCategory,
					partnerTypeId, amountSharable, sequence, playSource,
					partnerName, royaltyType, partnerType, commPercent,
					royaltyAmount, netAmount);
		}
	}


	
		@Override 
	  public List<DisbursementsData> retrieveAllPartnerName(Long
	  partnerType, Long mediaCategory) { // TODO Auto-generated method stub
	  context.authenticatedUser(); final String sql =
	  " SELECT DISTINCT a.partner_name as partnerName FROM bp_account a, bp_agreement_dtl g WHERE g.partner_type =? AND g.media_category =? and a.is_deleted='N' AND a.id = g.partner_account_id"
	  ; PartnerNameMapper mapper = new PartnerNameMapper(); return
	  jdbcTemplate.query(sql, mapper,new Object[]{partnerType,mediaCategory} );
	  
	  }
	  
	  private static final class PartnerNameMapper implements
	  RowMapper<DisbursementsData>{
	  
	  @Override public DisbursementsData mapRow(ResultSet rs, int rowNum)
	  throws SQLException {
	  
	  final String partnerName = rs.getString("partnerName");
	  
	  
	  return new DisbursementsData(partnerName); } }

	 

	@Override
	public Collection<OperatorDeductionData> getOperatorDeduction(Long clientId) {
		final String sql = "select id as id,client_id as clientId, ded_code as deductionCode, ded_value as deductionValue from bp_operator_deduction where client_id = ? order by id";
		OperatorDeductionMapper mapper = new OperatorDeductionMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { clientId });
	}

	final private static class OperatorDeductionMapper implements
			RowMapper<OperatorDeductionData> {

		@Override
		public OperatorDeductionData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final Long clientId = rs.getLong("clientId");
			final String deductionCode = rs.getString("deductionCode");
			final BigDecimal deductionValue = rs.getBigDecimal("deductionValue");
			return new OperatorDeductionData(id,clientId, deductionCode,
					deductionValue);
		}
	}

	@Override
	public Collection<OperatorDeductionData> getDeductionCodes() {
		final String sql = "select dc.id as id, dc.ded_code as deductionCode, dc.deduction as deduction, dc.ded_type as deductionType, dc.ded_source as deductionSource, dc.ded_category as deductionCategory from bp_deduction_codes dc";
		DeductionCodeMapper mapper = new DeductionCodeMapper();
		return jdbcTemplate.query(sql, mapper);
	}

	final private static class DeductionCodeMapper implements
			RowMapper<OperatorDeductionData> {
		@Override
		public OperatorDeductionData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String deductionCode = rs.getString("deductionCode");
			final String deduction = rs.getString("deduction");
			final String deductionType = rs.getString("deductionType");
			final String deductionSource = rs.getString("deductionSource");
			final String deductionCategory = rs.getString("deductionCategory");
			return new OperatorDeductionData(id, deductionCode, deduction,
					deductionType.charAt(0), deductionSource, deductionCategory);
		}
	}

	@Override
	public Long getPartnerId(String partnerName) {
		final String sql = "select pa.id as partnerId from bp_account pa where pa.partner_name = ? and pa.is_deleted='N'";
		return jdbcTemplate.queryForLong(sql, new Object[] { partnerName });
	}

	@Override
	public void retrieveProcedureforSettlementData() {
		// TODO Auto-generated method stub

		jdbcTemplate.update("call proc_settle_stg()", new Object[] {});

	}

	/*
	 * @Override public List<PartnerAccountData> retrieveCountryDetails() {
	 * context.authenticatedUser(); final String sql =
	 * "select bs.id as id,bs.country_name as countryName,bs.country_code as countryCode from b_country as bs"
	 * ; CountryCodeMapper mapper = new CountryCodeMapper(); return
	 * jdbcTemplate.query(sql, mapper); }
	 * 
	 * private static final class CountryCodeMapper implements
	 * RowMapper<PartnerAccountData> {
	 * 
	 * @Override public PartnerAccountData mapRow(ResultSet rs, int rowNum)
	 * throws SQLException { final Long id = rs.getLong("id"); final String
	 * country=rs.getString("countryName"); final String
	 * countryCode=rs.getString("countryCode"); return new
	 * PartnerAccountData(id, country,countryCode); } }
	 */
	@Override
	public Collection<RevenueSettlementSequenceData> retriveRevenueSettlementData(
			Long categoryId) {
		// TODO Auto-generated method stub
		context.authenticatedUser();
		 final String sql = "select DISTINCT a.id as partnerAccountId, a.partner_name as partnerName,d.royalty_sequence as royaltySequence ,"


				+ "(select partner_type from bp_royalty_seq where seq=1 and "
				+ "oem_id =a.id and active_flag='Y' ) as partnerType4,"
				+ "(select partner_type from bp_royalty_seq where seq=2 and "
				+ "oem_id =a.id and active_flag='Y' ) as partnerType5,"
				+ "(select partner_type from bp_royalty_seq where seq=3 and "
				+ "oem_id =a.id and active_flag='Y' ) as partnerType6 "
				+ "  from  bp_account a,bp_agreement g, bp_agreement_dtl d where d.media_category=? and a.id=g.partner_account_id and d.royalty_sequence=1 and d.partner_account_id = a.id";
		RevenueSettlementData mapper = new RevenueSettlementData();
		return jdbcTemplate.query(sql, mapper, new Object[] { categoryId });
	}

	private static final class RevenueSettlementData implements
			RowMapper<RevenueSettlementSequenceData> {
		@Override
		public RevenueSettlementSequenceData mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			final Long partnerAccountId = rs.getLong("partnerAccountId");
			final Long royaltySequence = rs.getLong("royaltySequence");
			final String partnerName = rs.getString("partnerName");
			final Long partnerType4 = rs.getLong("partnerType4");
			final Long partnerType5 = rs.getLong("partnerType5");
			final Long partnerType6 = rs.getLong("partnerType6");

			return new RevenueSettlementSequenceData(partnerAccountId,
					royaltySequence, partnerName, partnerType4, partnerType5,
					partnerType6);
		}
	}

	@Override
	public RevenueSettlementSequenceData retriveRevenueSettlementDefaultData() {
		// TODO Auto-generated method stub
		context.authenticatedUser();
		final String sql = "select DISTINCT (select partner_type from bp_royalty_seq where seq=1 and "
				+ "oem_id =0 and active_flag='Y' ) as partnerType1,  "
				+ "(select partner_type from bp_royalty_seq where seq=2 and "
				+ "oem_id =0 and active_flag='Y' ) as partnerType2,"
				+ "(select partner_type from bp_royalty_seq where seq=3 and "
				+ "oem_id =0 and active_flag='Y' ) as partnerType3 from bp_royalty_seq";
		RevenueSettlementDefaultData mapper = new RevenueSettlementDefaultData();
		return jdbcTemplate.queryForObject(sql, mapper, new Object[] {});
	}

	private static final class RevenueSettlementDefaultData implements
			RowMapper<RevenueSettlementSequenceData> {
		@Override
		public RevenueSettlementSequenceData mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			final Long partnerType1 = rs.getLong("partnerType1");
			final Long partnerType2 = rs.getLong("partnerType2");
			final Long partnerType3 = rs.getLong("partnerType3");

			final Long defaultData = 0L;

			return new RevenueSettlementSequenceData(partnerType1,
					partnerType2, partnerType3, defaultData);
		}
	}

	@Override
	public Long check(Long defaultData, Long seq, Long partnerType,
			String activeFlag) {
		// TODO Auto-generated method stub
		try {
			context.authenticatedUser();
			final String sql = "select id from bp_royalty_seq  where oem_id=? and seq=? and partner_type=? and Active_flag=? ";
			return jdbcTemplate.queryForLong(sql, new Object[] { defaultData,
					seq, partnerType, activeFlag });
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public void updateChecked(Long oemId) {
		// TODO Auto-generated method stub

		try {
			context.authenticatedUser();
			final String sql = "update bp_royalty_seq set active_flag='N' where active_flag='Y' and oem_id="
					+ oemId;
			jdbcTemplate.update(sql);
		} catch (final EmptyResultDataAccessException e) {
			return;
		}
	}

	@Override
	public List<PartnerAccountData> retrieveClientRoyaltyDetails(Long clientId) {
		String sql = "select (select code_value from m_code_value where id = c.royalty_type) as royaltyType from m_client c where c.id = ?";
		royaltyMapper mapper = new royaltyMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { clientId });
	}

	private final static class royaltyMapper implements
			RowMapper<PartnerAccountData> {
		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final String royaltyType = rs.getString("royaltyType");
			return new PartnerAccountData(royaltyType);
		}

	}

	@Override
	public List<StateDetails> retrieveAllStateDetails() {
		context.authenticatedUser();
		final String sql = "select id as id ,state_code as stateCode,state_name as stateName from b_state order by id asc";
		StateMapper mapper = new StateMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] {});

	}

	private static final class StateMapper implements RowMapper<StateDetails> {

		@Override
		public StateDetails mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			final Long id = rs.getLong("id");
			final String stateCode = rs.getString("stateCode");
			final String stateName = rs.getString("stateName");
			return new StateDetails(id, stateCode, stateName);
		}

	}

	@Override
	public List<InteractiveHeaderData> retriveAllInteractiveForThisClient(
			Long clientId) {
		/*final String sql = "select ih.id as id, ih.client_id as clientId, ih.client_external_id as externalId, (select mc1.code_value from m_code_value mc1 where mc1.id = ih.business_line) as businessLine, ih.activity_month as activityMonth, ih.data_upload_date as dataUploadedDate, (select mc2.code_value from m_code_value mc2 where mc2.id = ih.media_category) as mediaCategory,(select cc.charge_code from b_charge_codes cc where cc.id = ih.charge_code) as chargeCode from bp_interactive_header ih where ih.client_id=? and ih.is_deleted = 'N'";*/
		final String sql = "select ih.id as id, ih.client_id as clientId, ih.client_external_id as externalId, (select im.int_event_code from bp_intevent_master im where im.id = ih.business_line) as businessLine, ih.activity_month as activityMonth, ih.data_upload_date as dataUploadedDate, (select mc2.code_value from m_code_value mc2 where mc2.id = ih.media_category) as mediaCategory,(select cc.charge_code from b_charge_codes cc where cc.id = ih.charge_code) as chargeCode from bp_interactive_header ih where ih.client_id=? and ih.is_deleted = 'N'";
		
		InteractiveHeaderDataMapper mapper = new InteractiveHeaderDataMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { clientId });
	}

	final private static class InteractiveHeaderDataMapper implements
			RowMapper<InteractiveHeaderData> {
		@Override
		public InteractiveHeaderData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final Long clientId = rs.getLong("clientId");
			final Long externalId = rs.getLong("externalId");
			final String businessLineStr = rs.getString("businessLine");
			final Long activityMonth = rs.getLong("activityMonth");
			final LocalDate dataUploadedDate = JdbcSupport.getLocalDate(rs,
					"dataUploadedDate");
			final String mediaCategoryStr = rs.getString("mediaCategory");
			final String chargeCodeStr = rs.getString("chargeCode");
			return new InteractiveHeaderData(id, clientId, externalId,
					businessLineStr, activityMonth, dataUploadedDate.toDate(),
					mediaCategoryStr, chargeCodeStr);
		}
	}

	@Override
	public Collection<CurrencyData> retrieveCurrency() {
		final String sql = "select id as id,code as code from m_currency";
		Currencies codes = new Currencies();
		return jdbcTemplate.query(sql, codes);
	}

	private final static class Currencies implements RowMapper<CurrencyData> {
		@Override
		public CurrencyData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String code = rs.getString("code");
			return new CurrencyData(id, "", code, "", 0, "", "");
		}
	}

	@Override
	public InteractiveData retrieveInteractiveHeaderData(
			Long eventId) {
		final String sql = "select client_id as clientId, client_external_id as externalId, activity_month as activityMonth, data_upload_date as dataUploadDate,"
				+ "business_line as businessLine, media_category as mediaCategory, charge_code as chargeCode from bp_interactive_header where id = ?";
		
		InteractiveHeaderMapper mapper = new InteractiveHeaderMapper();
		
		return jdbcTemplate.queryForObject(sql,mapper,new Object[]{eventId});
	}
	
	
	private static final class InteractiveHeaderMapper implements RowMapper<InteractiveData>{
		
		@Override
		public InteractiveData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long clientId = rs.getLong("clientId");
			final Long externalId = rs.getLong("externalId");
			final Long activityMonth = rs.getLong("activityMonth");
			final LocalDate dataUploadDate = JdbcSupport.getLocalDate(rs, "dataUploadDate");
			final Long businessLine = rs.getLong("businessLine");
			final Long mediaCategory = rs.getLong("mediaCategory");
			final Long chargeCode = rs.getLong("chargeCode");
			return new InteractiveData(clientId,externalId,activityMonth,dataUploadDate!=null?dataUploadDate.toDate():null,businessLine,mediaCategory,chargeCode);
		}
		
	}
	
	
	@Override
	public Collection<InteractiveDetailsData> retriveInteractiveDetailsData(
			Long eventId) {
		final String sql = "select itd.play_source as playSource,"
				+ "itd.content_name as contentName,"
				+ "itd.content_provider as contentProvider,"
				+ "itd.channel_name as channelName,"
				+ "itd.service_name as serviceName,"
				+ "itd.end_user_price as endUserPrice, itd.downloads as downloads, itd.gross_revenue as grossRevenue from bp_interactive_detail "
				+ "itd where interactive_header_id=?";
		InteractiveDetailsMapper mapper = new InteractiveDetailsMapper();
		return jdbcTemplate.query(sql, mapper,new Object[]{eventId});
	}
	
	private static final class InteractiveDetailsMapper implements RowMapper<InteractiveDetailsData>{
		
		@Override
		public InteractiveDetailsData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long playSource = rs.getLong("playSource");
			final String contentName = rs.getString("contentName");
			final Long contentProvider = rs.getLong("contentProvider");
			final Long channelName = rs.getLong("channelName");
			final Long  serviceName = rs.getLong("serviceName");
			final BigDecimal endUserPrice = rs.getBigDecimal("endUserPrice");
			final BigDecimal downloads = rs.getBigDecimal("downloads");
			final BigDecimal grossRevenue = rs.getBigDecimal("grossRevenue");
			return new InteractiveDetailsData(playSource, contentName,
					contentProvider, channelName, serviceName, endUserPrice,
					downloads, grossRevenue);
		}
	}
	
	
	/*@Override
	public Collection<PartnerAccountData> retrieveEventDetails(Long eventId) {
		final String sql ="select (select code_value from m_code_value where id = itd.play_source) as playSource,"
				+ "(select title from b_media_asset where id = itd.content_name) as contentName,"
				+ "(select partner_name from bp_account where id = itd.content_provider) as contentProvider,"
				+ "(select partner_name from bp_account where id = itd.channel_name) as channelName,"
				+ "(select partner_name from bp_account where id = itd.service_name) as serviceName,"
				+ "itd.end_user_price as endUserPrice, itd.downloads as downloads, itd.gross_revenue as grossRevenue from bp_interactive_detail "
				+ "itd where interactive_header_id=?";
				
				
				"select itd.play_source as playSource,"
				+ "itd.content_name as contentName,"
				+ "itd.content_provider as contentProvider,"
				+ "itd.channel_name as channelName,"
				+ "itd.service_name as serviceName,"
				+ "itd.end_user_price as endUserPrice, itd.downloads as downloads, itd.gross_revenue as grossRevenue from bp_interactive_detail "
				+ "itd where interactive_header_id=?";
		
		
		 * select (select code_value from m_code_value where id = itd.play_source) as playSource,"
				+ "(select title from b_media_asset where id = itd.content_name) as contentName,"
				+ "(select partner_name from bp_account where id = itd.content_provider) as contentProvider,"
				+ "(select partner_name from bp_account where id = itd.channel_name) as channelName,"
				+ "(select partner_name from bp_account where id = itd.service_name) as serviceName,"
				+ "itd.end_user_price as endUserPrice, itd.downloads as downloads, itd.gross_revenue as grossRevenue from bp_interactive_detail "
				+ "itd where interactive_header_id=?";
		 * 

		ViewInteractiveEventMapper mapper = new ViewInteractiveEventMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { eventId });
	}*/

	/*private final static class ViewInteractiveEventMapper implements
			RowMapper<PartnerAccountData> {

		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long playSource = rs.getLong("playSource");
			final Long contentName = rs.getLong("contentName");
			final Long contentProvider = rs.getLong("contentProvider");
			final Long channelName = rs.getLong("channelName");
			final Long  serviceName = rs.getLong("serviceName");
			final BigDecimal endUserPrice = rs.getBigDecimal("endUserPrice");
			final BigDecimal downloads = rs.getBigDecimal("downloads");
			final BigDecimal grossRevenue = rs.getBigDecimal("grossRevenue");
			return new PartnerAccountData(playSource, contentName,
					contentProvider, channelName, serviceName, endUserPrice,
					downloads, grossRevenue);
			
			final String playSource = rs.getString("playSource");
			final String contentName = rs.getString("contentName");
			final String contentProvider = rs.getString("contentProvider");
			final String channelName = rs.getString("channelName");
			final String serviceName = rs.getString("serviceName");
			final BigDecimal endUserPrice = rs.getBigDecimal("endUserPrice");
			final BigDecimal downloads = rs.getBigDecimal("downloads");
			final BigDecimal grossRevenue = rs.getBigDecimal("grossRevenue");
			return new PartnerAccountData(playSource, contentName,
					contentProvider, channelName, serviceName, endUserPrice,
					downloads, grossRevenue);
			
		}

	}*/

	@Override
	public List<PartnerAccountData> retrieveAllPartnerType(String codeValue,
			String codeName) {

		final String sql = "select pa.id as partnerId ,pa.partner_name as partnerName from bp_account pa where pa.partner_type=(select id from m_code_value where code_value = ? and code_id=(select id from m_code where code_name= ?))";
		contentMapper mapper = new contentMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { codeValue,
				codeName });
	}

	private final static class contentMapper implements
			RowMapper<PartnerAccountData> {

		@Override
		public PartnerAccountData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long partnerId = rs.getLong("partnerId");
			final String partnerName = rs.getString("partnerName");
			return new PartnerAccountData(partnerId, partnerName);
		}

	}

	@Override
	public List<RevenueShareData> retriveAllrevenueshareForThisClient(
			Long clientId) {
		final String sql = "select sm.id as id,(select int_event_desc from bp_intevent_master where sm.business_line=id) as businessLine,"+
				"(select code_value from m_code_value where sm.media_category=id) as mediaCategory,"+
				"(select code_value from m_code_value where sm.revenue_share_type=id) as revenueShareType "+
				"from bp_revenue_share_master sm where client_id=?";
/*select sm.id as id,sm.business_line as businessLine ,sm.media_category as mediaCategory,sm.revenue_share_type as revenueShareType from bp_revenue_share_master sm where client_id=?*/;
		RevenueMapper mapper = new RevenueMapper();
		return jdbcTemplate.query(sql, mapper, new Object[] { clientId });
	}

	private final static class RevenueMapper implements
			RowMapper<RevenueShareData> {

		@Override
		public RevenueShareData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String businessLine = rs.getString("businessLine");
			final String mediaCategory = rs.getString("mediaCategory");
			final String revenueShareType = rs.getString("revenueShareType");
			return new RevenueShareData(id, businessLine, mediaCategory,
					revenueShareType);
		}

	}

	@Override
	public List<RevenueShareData> retriveSingleRevenueRecord(Long id) {
		final String sql ="select sp.start_value as startValue,sp.end_value as endValue,sp.percentage as percentage,"+
							"sp.flat as flat from bp_revenue_share_params sp,bp_revenue_share_master sm "+
							"where sp.revenue_share_master_id=sm.id and sp.revenue_share_master_id=?";
				/* "select rsm.id as id,rsm.business_line as businessLine,rsm.media_category as mediaCategory,"
				+ "rsm.revenue_share_type as revenueShareType,"
				+ "rsp.start_value as startValue,rsp.end_value as endValue,rsp.percentage as percentage,rsp.flat as flat "
				+ "from bp_revenue_share_master rsm,bp_revenue_share_params rsp "
				+ "where rsm.id=rsp.revenue_share_master_id and rsm.id=?";*/
		RevenueMapperSingleRow mapper = new RevenueMapperSingleRow();
		return jdbcTemplate.query(sql, mapper, new Object[] { id });
	}

	private final static class RevenueMapperSingleRow implements
			RowMapper<RevenueShareData> {

		@Override
		public RevenueShareData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			final Long startValue = rs.getLong("startValue");
			final Long endValue = rs.getLong("endValue");
			final BigDecimal percentage = rs.getBigDecimal("percentage");
			final BigDecimal flat=rs.getBigDecimal("flat");
			return new RevenueShareData(startValue, endValue, percentage, flat);
		}

	}

	@Override
	public RevenueShareData retriveEditRevenueRecord(Long id) {
		final String sql ="select rsm.id as id,rsm.business_line as businessLine,rsm.media_category as mediaCategory,"+
							"rsm.revenue_share_type as revenueShareType, rsm.client_id as clientId "+
							"from bp_revenue_share_master rsm "+
							"where rsm.id=?";
	
				RevenueMapperEditRow mapper = new RevenueMapperEditRow();
				return jdbcTemplate.queryForObject(sql, mapper, new Object[] { id });	}
	
		private final static class RevenueMapperEditRow implements
		RowMapper<RevenueShareData> {

		@Override
		public RevenueShareData mapRow(ResultSet rs, int rowNum)
		throws SQLException {
			final Long id = rs.getLong("id");
			final Long clientId = rs.getLong("clientId");
			final Long businessLine = rs.getLong("businessLine");
			final Long mediaCategory = rs.getLong("mediaCategory");
			final Long revenueShareType = rs.getLong("revenueShareType");
			return new RevenueShareData(id, businessLine, mediaCategory,
					revenueShareType,clientId);
}


}
		
		
	@Override
		public List<PartnerAgreementData> retrivePAmediaCategoryData(Long agmtId,
				Long mediaCategory, Long partnerType) {
			// TODO Auto-generated method stub
		context.authenticatedUser();
		final String sql = "SELECT id as id, media_category as mediaCategory,royalty_sequence as royaltySequence ,play_source as playSource ,royalty_share as royaltyShare, status as status from bp_agreement_dtl where  agmt_id =? and media_category =? and partner_type=? ";
		PAmediaCategoryData mapper = new PAmediaCategoryData();
		return jdbcTemplate.query(sql, mapper, new Object[] { agmtId ,mediaCategory,partnerType });
		}	
		
		
		
		@Override
		public List<PartnerAgreementData> retrivePAmediaCategoryData(
				Long agmtId) {
			// TODO Auto-generated method stub
			context.authenticatedUser();
			final String sql = "SELECT id as id, media_category as mediaCategory,royalty_sequence as royaltySequence ,play_source as playSource ,royalty_share as royaltyShare, status as status from bp_agreement_dtl where  agmt_id =? ";
			PAmediaCategoryData mapper = new PAmediaCategoryData();
			return jdbcTemplate.query(sql, mapper, new Object[] { agmtId });
		}

		private static final class PAmediaCategoryData implements
				RowMapper<PartnerAgreementData> {
			@Override
			public PartnerAgreementData mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				final Long id = rs.getLong("id");
				final Long mediaCategory=rs.getLong("mediaCategory");
				final Long royaltySequence = rs.getLong("royaltySequence");
				final Long playSource = rs.getLong("playSource");
				final Long royaltyShare = rs.getLong("royaltyShare");
				final Long status = rs.getLong("status");
				
				return new PartnerAgreementData(id,mediaCategory,royaltySequence, playSource, royaltyShare, status);
			}
		}
		
		
		@Override
		public Long checkPAgreementId(Long partnerAccountId, Long agreementType, Long agreementCategory,
				 Long royaltyType,Long settlementSource) {
			// TODO Auto-generated method stub
			try {
				context.authenticatedUser();
				final String sql = "select id  from `bp_agreement` where partner_account_id=? and agreement_type=? and agreement_category =? and royalty_type=? and   settle_source =? ";
				return jdbcTemplate.queryForLong(sql, new Object[] { partnerAccountId,
						agreementType,agreementCategory, royaltyType, settlementSource });
			} catch (final EmptyResultDataAccessException e) {
				return null;
			}

		}

    

		@Override
		public Collection<PartnerAccountData> retrieveAllCurrencyRateDetails() {
			final String sql = "select cr.id as id ,(select code from m_currency where id = cr.source_currency) as sourceCurrency,(select code from m_currency where id = cr.target_currency ) as targetCurrency,cr.exchange_rate as exchangeRate," +
					"cr.e_start_date as startDate,cr.e_end_date as endDate from bp_currency_rate cr where cr.is_deleted='N' order by cr.id asc";
			currencyMapper mapper=new currencyMapper();
			return jdbcTemplate.query(sql, mapper, new Object[]{});
		}
		
		private final static class currencyMapper implements
		RowMapper<PartnerAccountData> {
			
	   @Override
	    public PartnerAccountData mapRow(ResultSet rs, int rowNum) throws SQLException {
	     Long id = rs.getLong("id");   
		 String  sourceCurrency= rs.getString("sourceCurrency");
		 String  targetCurrency= rs.getString("targetCurrency");
		 BigDecimal exchangeRate = rs.getBigDecimal("exchangeRate");
		LocalDate startDate = JdbcSupport.getLocalDate(rs,"startDate");
		LocalDate endDate = JdbcSupport.getLocalDate(rs,"endDate");
		return new PartnerAccountData(id,sourceCurrency,targetCurrency,exchangeRate,startDate,endDate);
	   }

	}

		@Override
		public PartnerAccountData retrieveCurrencyRateDetail(Long id) {
	     	context.authenticatedUser();
			final String sql = "select cr.id as id , cr.source_currency as sourceCurrency,cr.target_currency as targetCurrency,cr.exchange_rate as exchangeRate," +
					"cr.e_start_date as startDate,cr.e_end_date as endDate from bp_currency_rate cr where id= ?";						
			EditCurrencyMapper mapper = new EditCurrencyMapper();
			return jdbcTemplate.queryForObject(sql, mapper, new Object[] { id });
		
	    }

		private static final class EditCurrencyMapper implements
	                                           RowMapper<PartnerAccountData> {
		   @Override
		    public PartnerAccountData mapRow(ResultSet rs, int rowNum) throws SQLException {
		      Long id = rs.getLong("id");   
			 Long  sourceCurrency= rs.getLong("sourceCurrency");
			 Long  targetCurrency= rs.getLong("targetCurrency");
			 BigDecimal exchangeRate = rs.getBigDecimal("exchangeRate");
			LocalDate startDate = JdbcSupport.getLocalDate(rs,"startDate");
			LocalDate endDate = JdbcSupport.getLocalDate(rs,"endDate");
			return new PartnerAccountData(id,sourceCurrency,targetCurrency,exchangeRate,startDate,endDate);
		   }
		}
		
		@Override
		public OperatorDeductionData getOperatorSingleDeductionCode(
				Long codeId) {
			final String sql = "select od.id as id, od.client_id as clientId,od.ded_code as dedCode, od.ded_value as dedValue from bp_operator_deduction od where od.id = ?";
			OperatorDeductionEditMapper mapper = new OperatorDeductionEditMapper();
			return jdbcTemplate.queryForObject(sql,mapper,new Object[]{codeId});
		}
		
		private static final class OperatorDeductionEditMapper implements RowMapper<OperatorDeductionData>{
			
			public OperatorDeductionData mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				final Long id = rs.getLong("id");
				final String deductionCode = rs.getString("dedCode"); 
				final BigDecimal deductionValue = rs.getBigDecimal("dedValue");
				final Long clientId = rs.getLong("clientId");
				
				return new OperatorDeductionData(id, clientId, deductionCode, deductionValue);			
			};
		}
}
