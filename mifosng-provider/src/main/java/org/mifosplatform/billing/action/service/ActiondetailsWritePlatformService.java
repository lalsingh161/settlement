package org.mifosplatform.billing.action.service;

import java.util.List;

import org.mifosplatform.billing.action.data.ActionDetaislData;

public interface ActiondetailsWritePlatformService {

	void AddNewActions(List<ActionDetaislData> actionDetaislDatas, Long clientId);

}
