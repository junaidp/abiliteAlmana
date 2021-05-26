package com.internalaudit.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.upload.AuditWorkProgramUpload;
import com.internalaudit.client.view.AuditEngagement.AddObservationUpload;
import com.internalaudit.client.view.AuditEngagement.AuditStepUploads;

public class ExceptionRow extends Composite {
	InternalAuditServiceAsync rpcService = GWT.create(InternalAuditService.class);
	private Label exId;
	HorizontalPanel panelFileDetail = new HorizontalPanel();
	private TextArea exception;
//	 private FileUpload fileUploadException;
	private HorizontalPanel hpnl;
	private Image removeRow;
	private int exceptionId;
	String mainFolder = "SamplingExceptionUploads";
	private AddObservationUpload addObservationUpload;
//	AuditStepUploads auditStepUpload = null;
//	private AuditWorkProgramUpload upload;

	public ExceptionRow() {
		hpnl = new HorizontalPanel();
		initWidget(hpnl);
		exId = new Label("0");
		layout();

	}

	public ExceptionRow(int exceptionId) {
		hpnl = new HorizontalPanel();
		hpnl.setWidth("1150px");
		this.exceptionId = exceptionId;
		initWidget(hpnl);
		exId = new Label(exceptionId + "");
		layout();
	}

	private void layout() {
		exId.addStyleName("hidden");

		exception = new TextArea();
		// fileUploadException = new FileUpload();
		removeRow = new Image("images/deleteIcon.png");
		removeRow.getElement().getStyle().setMarginTop(20, Unit.PX);
		// exception.addStyleName("txtExtendedWidth");
		// exception.setWidth("1130px");
		exception.addStyleName("w3-panel w3-border");
		exception.getElement().getStyle().setMarginTop(5, Unit.PX);
		exception.setSize("700px", "60px");

		hpnl.add(exId);
		hpnl.add(exception);
		hpnl.add(removeRow);
		// if (exceptionId != 0) {
		// String mainFolder = "AuditExceptions";
//		 upload = new AuditWorkProgramUpload(exceptionId + "", mainFolder);
//		 auditStepUpload = new AuditStepUploads(exId.getText());
		 addObservationUpload = new AddObservationUpload(exId.getText(), mainFolder);
		 hpnl.add(addObservationUpload);
//		 hpnl.add(upload);
		// // hpnl.add(panelFileDetail);
		// }

		exId.setVisible(false);
//		hpnl.add(removeRow);
	}

	public Label getExId() {
		return exId;
	}

	public void setExId(Label exId) {
		this.exId = exId;
	}

	public TextArea getException() {
		return exception;
	}

	public void setException(TextArea exception) {
		this.exception = exception;
	}

	public Image getRemoveRow() {
		return removeRow;
	}

	public void setRemoveRow(Image removeRow) {
		this.removeRow = removeRow;
	}

	public void removeRow() {
		exception.removeFromParent();
		exId.removeFromParent();
		removeRow.removeFromParent();
//		addObservationUpload.getDelete().setVisible(false);
	}

	public void disableFields() {
		try {
			exception.setEnabled(false);
			removeRow.setVisible(false);
			addObservationUpload.getDelete().setVisible(false);
			addObservationUpload.getUploadPanel().setVisible(false);
		} catch (Exception ex) {
			GWT.log("Error in disabling exception row " + ex);
		}
		// auditStepUpload.getUpload().setVisible(false);
		// auditStepUpload.getBtnSubmit().setVisible(false);
	}

	public AddObservationUpload getAddObservationUpload() {
		return addObservationUpload;
	}

	public void setAddObservationUpload(AddObservationUpload addObservationUpload) {
		this.addObservationUpload = addObservationUpload;
	}

//	public AuditStepUploads getAuditStepUpload() {
//		return auditStepUpload;
//	}
//
//	public void setAuditStepUpload(AuditStepUploads auditStepUpload) {
//		this.auditStepUpload = auditStepUpload;
//	}

}
