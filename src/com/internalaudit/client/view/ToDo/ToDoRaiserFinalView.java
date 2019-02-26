package com.internalaudit.client.view.ToDo;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.upload.EmailAttachmentUpload;
import com.internalaudit.client.view.ButtonRound;
import com.internalaudit.client.view.DisplayAlert;
import com.internalaudit.client.view.AuditEngagement.LabelHeading;
import com.internalaudit.shared.Employee;
import com.internalaudit.shared.JobCreation;
import com.internalaudit.shared.ToDo;
import com.internalaudit.shared.ToDoLogsEntity;

public class ToDoRaiserFinalView extends VerticalPanel {
	InternalAuditServiceAsync rpcService = GWT.create(InternalAuditService.class);
	VerticalPanel panelMain = new VerticalPanel();
	HorizontalPanel PanelUpButton = new HorizontalPanel();
	ButtonRound btnEmial = new ButtonRound("Email");
	ButtonRound btnPrint = new ButtonRound("Print");
	HorizontalPanel panelLabel = new HorizontalPanel();
	LabelHeading lblIr = new LabelHeading();
	Label lblIrData = new Label(":123");
	LabelHeading lblDate = new LabelHeading();
	Label lblDateData = new Label("28-sep-2018");
	Label lblSpace = new Label();
	LabelHeading lblRequestetBy = new LabelHeading();
	Label lblRequestedData = new Label("Hamza");
	Label lblMesssage;
	Label lblMesssageData;
	Label lblReplyOld;
	Label lblReplyOldData;

	Label lblReply = new Label("Reply");
	TextArea txtAreaReply = new TextArea();
	ButtonRound btnSubmit = new ButtonRound("Submit/Close");
	ButtonRound btnCancel = new ButtonRound("Cancel");
	ButtonRound btnRep = new ButtonRound("Reply");
	VerticalPanel panelMailRep = new VerticalPanel();
	VerticalPanel panelMail = new VerticalPanel();
	VerticalPanel panelReply = new VerticalPanel();
	HorizontalPanel panelFileDetail = new HorizontalPanel();
	private ToDoRaiserEntity toDORequest = null;

	public ToDoRaiserFinalView(ToDoRaiserEntity toDo) {

		this.toDORequest = toDo;
		setLayout(toDo);
		clickHandler(toDo);
	}

	private void clickHandler(final ToDoRaiserEntity toDo) {
		btnRep.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final ToDo todoEntity = new ToDo();
				todoEntity.setToDoId(toDo.getId());
				todoEntity.setDescription(txtAreaReply.getText());
				todoEntity.setRead(false);
				Employee assignedTo = new Employee();
				assignedTo.setEmployeeId(toDo.getRaisedToId());
				Employee assignedFrom = new Employee();
				assignedFrom.setEmployeeId(toDo.getRaisedById());
				todoEntity.setAssignedTo(assignedTo);
				todoEntity.setDueDate(toDo.getOverDueDays());
				todoEntity.setAssignedFrom(assignedFrom);
				JobCreation jobcreationId = new JobCreation();
				jobcreationId.setJobCreationId(toDo.getRelatedJobId());
				todoEntity.setJob(jobcreationId);

				final ToDoLogsEntity todoLogsEntity = new ToDoLogsEntity();

				todoLogsEntity.setDescription(toDo.getRequestedItem());
				todoLogsEntity.setRespond(txtAreaReply.getText());
				todoLogsEntity.setToDoId(toDo.getId());
				todoLogsEntity.setAssignedFrom(assignedFrom);
				todoLogsEntity.setAssignedTo(assignedTo);
				todoLogsEntity.setDate(toDo.getOverDueDays());

				saveToDoLog(todoEntity, todoLogsEntity);

			}

			private void saveToDoLog(final ToDo todoEntity, final ToDoLogsEntity todoLogsEntity) {
				rpcService.saveToDoLogs(todoLogsEntity, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("save ToDoLogsFailed");

					}

					@Override
					public void onSuccess(String result) {
						new DisplayAlert(result);
						saveToDo(todoEntity);

					}

					private void saveToDo(final ToDo todoEntity) {
						rpcService.savetoDo(todoEntity, new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("save Todo failed");
							}

							@Override
							public void onSuccess(String result) {

								new DisplayAlert(result);
							}
						});
					}
				});
			}
		});
	}

	private void setLayout(ToDoRaiserEntity toDo) {
		lblMesssageData = new Label();
		lblMesssage = new Label();
		for (int i = 0; i < toDo.getTodoLogList().size(); i++) {
			if (i == 0) {
				lblMesssage.setText("Message By ::" + toDo.getRaisedBy());
				lblMesssageData.setText(toDo.getTodoLogList().get(i).getDescription());
				panelMail.add(lblMesssage);
				panelMail.add(lblMesssageData);
			}
			lblReplyOld = new Label();
			lblReplyOldData = new Label();
			lblReplyOld.setText("Message By ::" + toDo.getRaisedTo());
			lblReplyOldData.setText(toDo.getTodoLogList().get(i).getRespond());
			panelMail.add(lblReplyOld);
			panelMail.add(lblReplyOldData);
		}

		//
		lblRequestedData.setText(toDo.getRaisedTo());
		lblDateData.setText(toDo.getOverDueDays().toString());
		lblIrData.setText(toDo.getId().toString());
		// lblReplyOldData.setText(toDo.getReply());
		setWidth("600px");
		setHeight("600px");
		panelMain.addStyleName("w3-border");
		panelLabel.setWidth("100%");
		lblSpace.getElement().getStyle().setPaddingLeft(300, Unit.PX);
		PanelUpButton.addStyleName(" w3-right");
		PanelUpButton.add(btnEmial);
		PanelUpButton.add(btnPrint);
		lblDateData.addStyleName("w3-panel");
		lblIrData.addStyleName("w3-panel");
		lblIr.setText("Ir#");
		lblDate.setText("Date");
		panelLabel.addStyleName("w3-border");
		panelLabel.add(lblIr);
		panelLabel.add(lblIrData);
		panelLabel.add(lblSpace);
		panelLabel.add(lblDate);
		panelLabel.add(lblDateData);
		panelMailRep.addStyleName("w3-border");
		panelReply.addStyleName("w3-border");
		panelMail.addStyleName("w3-border");
		HorizontalPanel panelRequestedBy = new HorizontalPanel();
		lblRequestetBy.setText("RequestedBy");
		panelRequestedBy.add(lblRequestetBy);
		lblRequestedData.addStyleName("w3-panel");
		panelRequestedBy.add(lblRequestedData);
		HorizontalPanel panelMailReq = new HorizontalPanel();
		panelMail.add(panelMailReq);
		panelMail.addStyleName("w3-gray");
		ScrollPanel panelMessageScroll = new ScrollPanel();
		panelMessageScroll.setHeight("200px");
		panelMessageScroll.add(panelMail);
		panelReply.add(lblReply);
		panelReply.add(txtAreaReply);
		panelMailRep.add(panelRequestedBy);
		panelMailRep.add(panelMessageScroll);
		panelMailRep.add(panelReply);
		HorizontalPanel panelPriority = new HorizontalPanel();
		Label lblPriority = new Label("Priority ::");
		ListBox listBoxPriority = new ListBox();
		listBoxPriority.addItem("High");
		listBoxPriority.addItem("Medium");
		listBoxPriority.addItem("Low");
		panelPriority.add(lblPriority);
		panelPriority.add(listBoxPriority);
		panelMailRep.add(panelPriority);
		panelPriority.setHeight("30px");
		panelPriority.addStyleName("w3-right");
		btnRep.getElement().getStyle().setMarginLeft(300, Unit.PX);
		HorizontalPanel panelbuttons = new HorizontalPanel();
		panelbuttons.add(btnSubmit);
		panelbuttons.add(btnRep);
		panelbuttons.add(btnCancel);
		panelMailRep.add(panelbuttons);
		EmailAttachmentUpload a = new EmailAttachmentUpload();
		VerticalPanel panelFileUpload = new VerticalPanel();
		panelFileUpload.add(a);
		txtAreaReply.getElement().setPropertyString("placeholder", "Enter your Reply here");
		PanelUpButton.setHeight("30px");
		panelLabel.setHeight("40px");
		panelMailRep.setHeight("320px");
		panelFileUpload.setHeight("50px");
		panelMail.setHeight("250px");
		txtAreaReply.setHeight("100px");
		panelReply.setWidth("600px");
		panelMail.setWidth("590px");
		txtAreaReply.setWidth("590px");

		add(PanelUpButton);
		add(panelLabel);
		add(panelMailRep);

	}

	private void fetchEmailAttachments() {
		rpcService.fetchEmailAttachments(new AsyncCallback<ArrayList<String>>() {
			FlexTable records = new FlexTable();

			@Override
			public void onSuccess(ArrayList<String> result) {
				for (int i = 0; i < result.size(); i++) {
					final Anchor lblfilename = new Anchor(result.get(i));
					Label lblFileAttached = new Label("Attached");
					lblfilename.addStyleName("pointerStyle");
					lblfilename.getElement().getStyle().setTextDecoration(TextDecoration.NONE);
					lblfilename.setHeight("25px");
					lblFileAttached.setHeight("25px");
					records.setWidth("100%");
					records.setWidget(i, 0, lblfilename);
					records.setWidget(i, 1, lblFileAttached);
					if (i % 2 != 0) {
						records.getRowFormatter().addStyleName(i, "jobStatusRow");
					}
					panelFileDetail.setWidth("100%");
					panelFileDetail.add(records);
					lblfilename.setWordWrap(false);
					String upperCasedJobLink = lblfilename.getText();
					lblfilename.setText(upperCasedJobLink);
					lblfilename.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {

							Window.open("/EmailAttachmentUpload/" + lblfilename.getText(), "name", "");
						}
					});
				}

			}

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("fetchEmailAttachment Failed");
			}

		});
	}

	public ButtonRound getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(ButtonRound btnCancel) {
		this.btnCancel = btnCancel;
	}

}