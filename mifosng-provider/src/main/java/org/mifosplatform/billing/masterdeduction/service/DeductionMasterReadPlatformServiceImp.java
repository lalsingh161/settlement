package org.mifosplatform.billing.masterdeduction.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mifosplatform.billing.address.data.StateDetails;
import org.mifosplatform.billing.masterdeduction.data.DeductionMasterData;
import org.mifosplatform.billing.masterdeduction.domain.DeductionJpaRepository;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service
public class DeductionMasterReadPlatformServiceImp implements DeductionMasterReadPlatformService {

	final private PlatformSecurityContext context;
	final private DeductionJpaRepository deductionJpaRepository;
	final private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	public DeductionMasterReadPlatformServiceImp(
			final PlatformSecurityContext context,
			final DeductionJpaRepository deductionJpaRepository,
			final TenantAwareRoutingDataSource dataSource
			) {
		this.context = context;
		this.deductionJpaRepository = deductionJpaRepository;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	
	@Override
	public List<DeductionMasterData> retrieveAllDeductionDetails() {
	
		context.authenticatedUser();
		final String sql = " SELECT id as id ,ded_code as deductionCode,deduction as deductionName,ded_type as deductionType,ded_source as levelApplicable,ded_category as business,Customer_type as customerType,circle as circle FROM bp_deduction_codes dc where dc.is_deleted='N' order by dc.id asc";
		DeductionMapper mapper = new DeductionMapper();
		return jdbcTemplate.query(sql, mapper,new Object[]{} );
	
	}
	
	private static final class DeductionMapper implements RowMapper<DeductionMasterData>{
		@Override
		public DeductionMasterData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			final Long id = rs.getLong("id");
			final String  deductionCode = rs.getString("deductionCode");
			final String  deductionName = rs.getString("deductionName");
			final String  deductionType = rs.getString("deductionType");
			final String  levelApplicable = rs.getString("levelApplicable");
			final String  business = rs.getString("business");
			final Long   customerType = rs.getLong("customerType");
			final String  circle = rs.getString("circle");
			
			
			return new DeductionMasterData(id,deductionCode,deductionName,deductionType,levelApplicable,business,customerType,circle);
		}
	}

	@Override
	public DeductionMasterData retrieveDeductionDetail(Long id) {
		context.authenticatedUser();
		final String sql = " SELECT id as id ,ded_code as deductionCode,deduction as deductionName,ded_type as deductionType,ded_source as levelApplicable,ded_category as business,Customer_type as customerType,circle as circle FROM bp_deduction_codes dc where dc.id=?";
		EditDeductionMapper mapper = new EditDeductionMapper();
		return jdbcTemplate.queryForObject(sql, mapper, new Object[] { id });
	
    }
	private static final class EditDeductionMapper implements
	                                           RowMapper<DeductionMasterData> {
		
     @Override
     public DeductionMasterData mapRow(ResultSet rs, int rowNum)
		throws SQLException {
 		final Long id = rs.getLong("id");
		final String  deductionCode = rs.getString("deductionCode");
		final String  deductionName = rs.getString("deductionName");
		final String  deductionType = rs.getString("deductionType");
		final String  levelApplicable = rs.getString("levelApplicable");
		final String  business = rs.getString("business");
		final Long   customerType = rs.getLong("customerType");
		final String  circle = rs.getString("circle");
	return new DeductionMasterData(id,deductionCode,deductionName,deductionType,levelApplicable,business,customerType,circle);
  }
   }
	
	@Override
	public List<StateDetails> retrieveAllStateDetails() {
		context.authenticatedUser();
		final String sql = "select id as id ,state_code as stateCode,state_name as stateName from b_state order by id asc";
		StateMapper mapper = new StateMapper();
		return jdbcTemplate.query(sql, mapper,new Object[]{} );
	
	}
	
	private static final class StateMapper implements  RowMapper<StateDetails>{

		@Override
		public StateDetails mapRow(ResultSet rs, int rowNum)
				throws SQLException {
		
			final Long id = rs.getLong("id");
			final String  stateCode = rs.getString("stateCode");
			final String stateName = rs.getString("stateName");
			return new StateDetails(id,stateCode,stateName);
		}
	
	}
	
}
