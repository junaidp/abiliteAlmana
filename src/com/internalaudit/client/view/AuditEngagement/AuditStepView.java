package com.internalaudit.client.view.AuditEngagement;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.view.AmendmentPopup;
import com.internalaudit.client.view.data.AuditStepData;
import com.internalaudit.client.widgets.ExceptionRow;
import com.internalaudit.shared.AuditStep;
import com.internalaudit.shared.AuditWork;
import com.internalaudit.shared.Employee;
import com.internalaudit.shared.Exceptions;
import com.internalaudit.shared.InternalAuditConstants;
import com.internalaudit.shared.JobCreation;

public class AuditStepView extends Composite {

	@UiField
	TextArea performance;

	@UiField
	Label auditStepId;

	@UiField
	TextBox population;

	@UiField
	TextBox sample;

	@UiField
	TextBox selection;

	@UiField
	ListBox conclusion;
	@UiField
	Button addException;
	// @UiField
	// Button save;
	// @UiField
	// Button submit;
	// @UiField
	// Button reject;
	// @UiField
	// Button approve;
	@UiField
	HorizontalPanel approvalButtonsPanel;
	@UiField
	HorizontalPanel initiationButtonsPanel;

	// @UiField
	// Button addException;
	@UiField
	HorizontalPanel panelAddException;

	@UiField
	VerticalPanel exceptions;
	@UiField
	Label submittedBy;
	@UiField
	Label lblObservations;
	@UiField
	Label approvedBy;
	@UiField
	Image imgApproved;
	@UiField
	HorizontalPanel feedbackPanel;
	@UiField
	Label feedback;
	@UiField
	VerticalPanel uploadFileContainer;
	@UiField
	VerticalPanel panelSamplingAudit;

	private static AuditViewUiBinder uiBinder = GWT.create(AuditViewUiBinder.class);
	private AuditStepData viewData = new AuditStepData();
	private Employee loggedInEmployee;
	private AuditWork auditWork;
	private SamplingAuditStep auditStepSamplingView;
	private int selectedJobId;
	private Button save = new Button("Save");
	private Button submit = new Button("Submit");
	private Button approve = new Button("Approve");
	private Button reject = new Button("FeedBack");
	// private Button addException = new Button("Add Observation");
	private ArrayList<ExceptionRow> arrayListExceptions;
	private ArrayList<AddObservationUpload> listExceptionUploads;

	interface AuditViewUiBinder extends UiBinder<Widget, AuditStepView> {
	}

	public AuditStepView(final AuditWork auditWork, final int selectedJobId, Employee loggedInEmployee) {

		initWidget(uiBinder.createAndBindUi(this));
		this.loggedInEmployee = loggedInEmployee;
		// addException.setWidth("110px");
		this.auditWork = auditWork;
		this.selectedJobId = selectedJobId;
		listExceptionUploads = new ArrayList<AddObservationUpload>();
		arrayListExceptions = new ArrayList<ExceptionRow>();
		performance.addStyleName("messageTextarea");
		// panelAddException.add(addException);
		lblObservations.setVisible(false);
		initiationButtonsPanel.add(save);
		initiationButtonsPanel.add(submit);
		approvalButtonsPanel.add(approve);
		approvalButtonsPanel.add(reject);
		initiationButtonsPanel.getElement().getStyle().setMarginLeft(980, Unit.PX);
		approvalButtonsPanel.getElement().getStyle().setMarginLeft(980, Unit.PX);
		conclusion.addStyleName("listTextBold");
		auditStepSamplingView = new SamplingAuditStep(auditWork.getAuditWorkId() + "");
		panelSamplingAudit.add(auditStepSamplingView);
		auditStepSamplingView.getTxtAreaControl().setText(auditWork.getDescription());
		addException.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				lblObservations.setVisible(true);
				final ExceptionRow row = new ExceptionRow();
				exceptions.add(row);
				arrayListExceptions.add(row);
				listExceptionUploads.add(row.getAddObservationUpload());
				save.setVisible(false);
				// temporarily removed//
				save.setVisible(true);
				conclusion.setEnabled(true);

				row.getRemoveRow().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						row.removeRow();
						for (int i = 0; i < exceptions.getWidgetCount(); i++) {
							if (exceptions.getWidget(i) == row) {
								exceptions.remove(i);
								arrayListExceptions.remove(i);
								listExceptionUploads.remove(i);
							}
						}
						if (exceptions.getWidgetCount() == 0) {
							lblObservations.setVisible(false);
						}
					}
				});

			}
		});
		viewData.getSavedAuditStep(this, auditStepSamplingView, selectedJobId, auditWork, exceptions, loggedInEmployee);

		// viewData.getSavedExceptions(exceptions, selectedJobId );

		setHandlers(auditWork, selectedJobId, auditStepSamplingView);
	}

	private void setHandlers(final AuditWork auditWork, final int selectedJobId,
			final SamplingAuditStep auditStepSamplingView) {

		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				// disableFields(exceptions);
				saveAuditStep(auditWork, selectedJobId, auditStepSamplingView, InternalAuditConstants.SAVED, "");

			}

		});

		approve.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {

				saveAuditStep(auditWork, selectedJobId, auditStepSamplingView, InternalAuditConstants.APPROVED, "");
				disableFields(exceptions, auditStepSamplingView);
			}

		});

		reject.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				final AmendmentPopup amendmentPopup = new AmendmentPopup();
				amendmentPopup.popupAmendment();

				amendmentPopup.getBtnSubmit().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						disableFields(exceptions, auditStepSamplingView);
						saveAuditStep(auditWork, selectedJobId, auditStepSamplingView, InternalAuditConstants.REJECTED,
								amendmentPopup.getComments().getText());
						amendmentPopup.getPopupComments().removeFromParent();
					}
				});

			}
		});
		submit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				boolean confirm = Window.confirm("Are you done with this Audit Step ?");
				if (confirm) {

					saveAuditStep(auditWork, selectedJobId, auditStepSamplingView, InternalAuditConstants.SUBMIT, "");
					disableFields(exceptions, auditStepSamplingView);
				}
			}

		});
	}
		

	private void saveAuditStep(final AuditWork auditWork, final int selectedJobId,
			SamplingAuditStep auditStepSamplingView2, int status, String feedback) {
		// disableFields(exceptions);
		boolean flagfSave = true;
		AuditStep step = new AuditStep();
		ArrayList<Exceptions> exs = new ArrayList<Exceptions>();
		step.setFeedback(feedback);
		step.setFrequency(auditStepSamplingView2.getListBoxFrequency().getSelectedIndex());
		step.setSamplingMethod(auditStepSamplingView2.getListBoxSamplingMethod().getSelectedIndex());
		step.setControlList(auditStepSamplingView2.getListBoxControlList().getSelectedIndex());
		step.setPopulation(auditStepSamplingView2.getLblPopulationData().getText());
		step.setProceducePerformance(auditStepSamplingView2.getTxtAreaAuditProcedure().getText());
		step.setSampleSelected(auditStepSamplingView2.getLblSampleSizeData().getText());
		step.setSelectionBasis(selection.getText());
		step.setConclusion(conclusion.getItemText(conclusion.getSelectedIndex()));
		step.setJobId(selectedJobId);
		step.setAuditStepId(Integer.parseInt(auditStepId.getText()));
		step.setAuditWork(auditWork);
		step.setStatus(status);

		if (status == InternalAuditConstants.SUBMIT || status == InternalAuditConstants.SAVED) {

			Employee initiatedBy = new Employee();
			initiatedBy = loggedInEmployee;
			step.setInitiatedBy(initiatedBy);

			Employee approvedBy = new Employee();
			approvedBy.setEmployeeId(0);
			step.setApprovedBy(approvedBy);
		} else if (status == InternalAuditConstants.APPROVED || status == InternalAuditConstants.REJECTED) {
			Employee approvedBy = new Employee();
			approvedBy = loggedInEmployee;
			step.setApprovedBy(approvedBy);
		}

		for (int i = 0; i < exceptions.getWidgetCount(); i++) {
			Exceptions exception = new Exceptions();
			String observationText = ((ExceptionRow) exceptions.getWidget(i)).getException().getText(); 
			if(observationText.length() < 1) {
				flagfSave = false;
				break;
			}
			exception.setDetail(observationText);
			// change
			JobCreation jobCreation = new JobCreation();
			jobCreation.setJobCreationId(selectedJobId);
			exception.setJobCreationId(jobCreation);
			// exception.setJobCreationId(selectedJobId);
			exception.setExceptionId(Integer.parseInt(((ExceptionRow) exceptions.getWidget(i)).getExId().getText()));
			exception.setAuditStep(step.getAuditStepId());
			exs.add(exception);
		}
		if(flagfSave) {
			viewData.saveAuditStepAndException(step, exs, status, this);
		}
		else
		{
			Window.alert("Please add some text in Observation"); 
		}
	}

	public void fetchSavedAuditStep() {
		viewData.getSavedAuditStep(this, auditStepSamplingView, selectedJobId, auditWork, exceptions, loggedInEmployee);

	}

	public TextArea getPerformance() {
		return performance;
	}

	public void setPerformance(TextArea performance) {
		this.performance = performance;
	}

	public TextBox getPopulation() {
		return population;
	}

	public HorizontalPanel getInitiationButtonsPanel() {
		return initiationButtonsPanel;
	}

	public void setInitiationButtonsPanel(HorizontalPanel initiationButtonsPanel) {
		this.initiationButtonsPanel = initiationButtonsPanel;
	}

	public void setPopulation(TextBox population) {
		this.population = population;
	}

	public TextBox getSample() {
		return sample;
	}

	public void setSample(TextBox sample) {
		this.sample = sample;
	}

	public TextBox getSelection() {
		return selection;
	}

	public void setSelection(TextBox selection) {
		this.selection = selection;
	}

	public ListBox getConclusion() {
		return conclusion;
	}

	public void setConclusion(ListBox conclusion) {
		this.conclusion = conclusion;
	}

	public Label getAuditStepId() {
		return auditStepId;
	}

	public void setAuditStepId(Label auditStepId) {
		this.auditStepId = auditStepId;
	}

	public Button getReject() {
		return reject;
	}

	public void setReject(Button reject) {
		this.reject = reject;
	}

	public Button getApprove() {
		return approve;
	}

	public void setApprove(Button approve) {
		this.approve = approve;
	}

	public HorizontalPanel getApprovalButtonsPanel() {
		return approvalButtonsPanel;
	}

	public void setApprovalButtonsPanel(HorizontalPanel approvalButtonsPanel) {
		this.approvalButtonsPanel = approvalButtonsPanel;
	}

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public void disableFields(VerticalPanel exceptions, SamplingAuditStep auditSamplingView) {
		performance.setEnabled(false);
		auditSamplingView.getTxtAreaControl().setEnabled(false);
		population.setEnabled(false);
		sample.setEnabled(false);
		selection.setEnabled(false);
		conclusion.setEnabled(false);
		initiationButtonsPanel.setVisible(false);
		approvalButtonsPanel.setVisible(false);
		addException.setVisible(false);
		auditSamplingView.getLblPopulationData().setEnabled(false);
		auditSamplingView.getLblSampleSizeData().setEnabled(false);
		auditSamplingView.getTxtAreaAuditProcedure().setEnabled(false);
		auditSamplingView.getListBoxControlList().setEnabled(false);
		auditSamplingView.getListBoxFrequency().setEnabled(false);
		auditSamplingView.getAnchorExcelTemplate().setVisible(false);
		auditSamplingView.getSamplingFileUploader().setVisible(false);
		auditSamplingView.getListBoxSamplingMethod().setEnabled(false);
		auditSamplingView.fileUpload.getBtnUpload().setVisible(false);
		auditSamplingView.fileUpload.getUploadPanel().setVisible(false);
		auditSamplingView.getFileUpload().getDelete().setVisible(false);
		// Window.alert(exceptions.getWidgetCount() + "");
		for (int i = 0; i < exceptions.getWidgetCount(); i++) {
			ExceptionRow exceptionRow = (ExceptionRow) exceptions.getWidget(i);
			// exceptionRow.getAuditStepUpload().getBtnSubmit().setVisible(false);
			// exceptionRow.getAuditStepUpload().getUpload().setVisible(false);
			exceptionRow.disableFields();
		}

	}

	public void enableFields() {
		performance.setEnabled(true);
		population.setEnabled(true);
		sample.setEnabled(true);
		selection.setEnabled(true);
		conclusion.setEnabled(true);
		initiationButtonsPanel.setVisible(true);
		addException.setVisible(true);
		save.setVisible(true);
		submit.setVisible(true);
		// approvalButtonsPanel.setVisible(false);
	}

	public void supervisorView() {
		performance.setEnabled(true);
		population.setEnabled(true);
		sample.setEnabled(true);
		selection.setEnabled(true);
		conclusion.setEnabled(true);
		approvalButtonsPanel.setVisible(true);
		initiationButtonsPanel.setVisible(false);
		addException.setVisible(false);
		}

	public Label getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(Label submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Label getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Label approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Image getImgApproved() {
		return imgApproved;
	}

	public void setImgApproved(Image imgApproved) {
		this.imgApproved = imgApproved;
	}

	public HorizontalPanel getFeedbackPanel() {
		return feedbackPanel;
	}

	public void setFeedbackPanel(HorizontalPanel feedbackPanel) {
		this.feedbackPanel = feedbackPanel;
	}

	public Label getFeedback() {
		return feedback;
	}

	public void setFeedback(Label feedback) {
		this.feedback = feedback;
	}

	public VerticalPanel getUploadFileContainer() {
		return uploadFileContainer;
	}

	public void setUploadFileContainer(VerticalPanel uploadFileContainer) {
		this.uploadFileContainer = uploadFileContainer;
	}

	public Button getSubmit() {
		return submit;
	}

	public void setSubmit(Button submit) {
		this.submit = submit;
	}

	public ArrayList<ExceptionRow> getArrayListExceptions() {
		return arrayListExceptions;
	}

	public void setArrayListExceptions(ArrayList<ExceptionRow> arrayListExceptions) {
		this.arrayListExceptions = arrayListExceptions;
	}

	public ArrayList<AddObservationUpload> getListExceptionUploads() {
		return listExceptionUploads;
	}

	public void setListExceptionUploads(ArrayList<AddObservationUpload> listExceptionUploads) {
		this.listExceptionUploads = listExceptionUploads;
	}

}
