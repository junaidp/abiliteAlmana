
package com.internalaudit.client.view.ToDo;
import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface InformationRequestReceiverProperties extends PropertyAccess<InformationRequestRaiseEntity> {
	@Path("id")
	ModelKeyProvider<InformationRequestReceiverEntity> key();

	ValueProvider<InformationRequestReceiverEntity, String> requestedItem();

	ValueProvider<InformationRequestReceiverEntity, String> raisedBy();

	ValueProvider<InformationRequestReceiverEntity, String> relatedJob();
	
	ValueProvider<InformationRequestReceiverEntity, String> status();
	
	ValueProvider<InformationRequestReceiverEntity, String> viewButton();
	
	ValueProvider<InformationRequestReceiverEntity, Date> overDueDays();

	ValueProvider<InformationRequestReceiverEntity, Integer> id();
	
	ValueProvider<InformationRequestReceiverEntity, Integer> raisedById();
	
	ValueProvider<InformationRequestReceiverEntity, Integer> raisedToId();
	
	ValueProvider<InformationRequestReceiverEntity, String> contactEmail();
	
	ValueProvider<InformationRequestReceiverEntity, Integer> sstatus();
	
	ValueProvider<InformationRequestReceiverEntity, Boolean> sendReminder();
	
	ValueProvider<InformationRequestReceiverEntity, Boolean> sendNotification();
	
	ValueProvider<InformationRequestReceiverEntity, Integer> relatedJobId();
	

}
