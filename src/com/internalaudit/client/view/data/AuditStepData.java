package com.internalaudit.client.view.data;

import java.io.Console;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.view.DisplayAlert;
import com.internalaudit.client.view.LoadingPopup;
import com.internalaudit.client.view.AuditEngagement.AddObservationUpload;
import com.internalaudit.client.view.AuditEngagement.AuditStepUploads;
import com.internalaudit.client.view.AuditEngagement.AuditStepView;
import com.internalaudit.client.view.AuditEngagement.SamplingAuditStep;
import com.internalaudit.client.widgets.ExceptionRow;
import com.internalaudit.shared.AuditStep;
import com.internalaudit.shared.AuditWork;
import com.internalaudit.shared.Employee;
import com.internalaudit.shared.Exceptions;
import com.internalaudit.shared.InternalAuditConstants;
import com.internalaudit.shared.TimeOutException;

public class AuditStepData {

	private InternalAuditServiceAsync rpcService = GWT.create(InternalAuditService.class);
	private Logger logger = Logger.getLogger("AuditStepData");
	private ArrayList<ExceptionRow> alreadySavedExceptions;

	public AuditStepData() {

	}

	public void saveAuditStepAndException(AuditStep step, ArrayList<Exceptions> exs, final int status,
			final AuditStepView auditStepView) {

		final LoadingPopup loadingPopup = new LoadingPopup();
		loadingPopup.display();
		rpcService.saveAuditStepAndExceptions(step, exs, new AsyncCallback<ArrayList<Exceptions>>() {

			@Override
			public void onFailure(Throwable caught) {
				loadingPopup.remove();
				System.out.println("Fail RPC:saveAuditStepAndException  In Audit Step Data");

				logger.log(Level.INFO, "FAIL: saveAuditStepAndExceptions .Inside Audit AuditAreaspresenter");
				if (caught instanceof TimeOutException) {
					History.newItem("login");
				} else {
					System.out.println("FAIL: saveAuditStepAndExceptions .Inside AuditAreaspresenter");
					Window.alert("FAIL: saveAuditStepAndExceptions");// After
																		// FAIL
																		// ...
																		// write
																		// RPC
																		// Name
																		// NOT
																		// Method
																		// Name..
				}

			}

			@Override
			public void onSuccess(ArrayList<Exceptions> exceptions) {
				loadingPopup.remove();
				auditStepView.getFeedbackPanel().setVisible(false);
				for(int i=0; i<exceptions.size(); i++) {
//					if(alreadySavedExceptions != null && !alreadySavedExceptions.isEmpty()) {
						if(auditStepView.getListExceptionUploads().get(i).getLblfilename() == null) {
							auditStepView.getListExceptionUploads().get(i).saveUploadException(String.valueOf(exceptions.get(i).getExceptionId()));
					}
//					else {
//					auditStepView.getListExceptionUploads().get(i).saveUploadException(String.valueOf(exceptions.get(i).getExceptionId()));
//					}
				}
				if (status == 3) {
					new DisplayAlert("Audit Step Saved");
					auditStepView.fetchSavedAuditStep();
				} else if (status == 1) {
					new DisplayAlert("Audit Step Approved");
				} else if (status == 2) {
					new DisplayAlert("Feedback submitted");
				} else if (status == 4) {
					new DisplayAlert("Audit Step Submitted");
				}
			}

		});

	}

	private void updateFileNameInDatabase(int auditStepId) {
		InternalAuditServiceAsync rpcService = GWT.create(InternalAuditService.class);
		rpcService.updateUploadedAuditStepFile(auditStepId, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable arg0) {
				System.out.println("fail: updateFileNameInDatabase");
			}

			@Override
			public void onSuccess(String result) {
				System.out.println(result);
			}
		});

	}

	public void getSavedAuditStep(final AuditStepView auditStepView, final SamplingAuditStep auditSamplingView,
			int selectedJobId, final AuditWork auditWork, final VerticalPanel exceptions,
			final Employee loggedInEmployee) {

		rpcService.getSavedAuditStep(selectedJobId, auditWork.getAuditWorkId(), new AsyncCallback<AuditStep>() {

			@Override
			public void onFailure(Throwable caught) {

				logger.log(Level.INFO, "FAIL: getSavedAuditStep .Inside Audit AuditAreaspresenter");
				if (caught instanceof TimeOutException) {
					History.newItem("login");
				} else {
					System.out.println("FAIL: getSavedAuditStep .Inside AuditAreaspresenter");
					Window.alert("FAIL: getSavedAuditStep");// After FAIL ...
															// write RPC Name
															// NOT Method Name..
				}

			}

			@Override
			public void onSuccess(final AuditStep auditStep) {
				auditSamplingView.getListBoxSamplingMethod().setSelectedIndex(auditStep.getSamplingMethod());
				auditSamplingView.getListBoxControlList().setSelectedIndex(auditStep.getControlList());
				auditSamplingView.getListBoxFrequency().setSelectedIndex(auditStep.getFrequency());
				auditSamplingView.getLblSampleSizeData().setText(auditStep.getSampleSelected());
				auditSamplingView.getLblPopulationData().setText(auditStep.getPopulation());
				auditSamplingView.getTxtAreaAuditProcedure().setText(auditStep.getProceducePerformance());
				if (auditStep.getAuditStepId() != 0) {
					// auditStepView.disableFields(exceptions);
					displayExceptions(exceptions, auditStep.getExceptions(), auditStepView);
					// auditStepView.disableFields(exceptions,
					// auditSamplingView);
					//// CHECK WHO IS LOGGEDIN
					/// To show feedback
					if (auditStep.getFeedback() != null && !auditStep.getFeedback().isEmpty()) {
						auditStepView.getFeedbackPanel().setVisible(true);
						auditStepView.getFeedback().setText(auditStep.getFeedback());
						auditStepView.getInitiationButtonsPanel().setVisible(true);
						auditStepView.getSave().setVisible(true);
						auditStepView.getSubmit().setVisible(true);
					}
					//// end feedback
					// if(auditWorks.size()<1){//
					// enableInitiationpanel();
					// enableFields();
					// }else{// Just to show Approved By/ Submitted By ...
					if (auditStep.getApprovedBy() != null && auditStep.getApprovedBy().getEmployeeId() != 0
							&& auditStep.getStatus() == InternalAuditConstants.APPROVED) {
						auditStepView.getApprovedBy().setVisible(true);
						auditStepView.getApprovedBy()
								.setText("Approved by:" + auditStep.getApprovedBy().getEmployeeName());
						if (auditStep.getApprovedBy().getRollId() == 1) {
							auditStepView.getImgApproved().setVisible(true);
						}
						auditStepView.getSubmittedBy().setVisible(true);
						auditStepView.getSubmittedBy()
								.setText("Initiated by:" + auditStep.getInitiatedBy().getEmployeeName() + "::");
					}
					// }

					// if(auditWork.getJobCreationId().getAuditHead() ==
					// loggedInEmployee.getEmployeeId()){
					// auditStepView.supervisorView();
					// if(auditStep.getStatus() ==
					// InternalAuditConstants.APPROVED || auditStep.getStatus()
					// == InternalAuditConstants.REJECTED){
					// auditStepView.getApprove().setEnabled(false);
					// auditStepView.getReject().setEnabled(false);
					// }
					//
					// }else{
					// auditStepView.getApprovalButtonsPanel().setVisible(false);
					// auditStepView.getSave().setVisible(true);
					// if(auditStep.getStatus() ==
					// InternalAuditConstants.APPROVED || auditStep.getStatus()
					// == InternalAuditConstants.INITIATED){
					// auditStepView.disableFields();
					// auditStepView.getSave().setEnabled(false);
					// }
					// }

					if ((auditStep.getInitiatedBy() != null && auditStep.getInitiatedBy().getReportingTo()
							.getEmployeeId() == loggedInEmployee.getEmployeeId() || loggedInEmployee.getRollId() == 1)
							&& (auditStep.getStatus() == InternalAuditConstants.SUBMIT)) {
						auditStepView.supervisorView();

					}

					else if (auditStep.getInitiatedBy() != null
							&& auditStep.getInitiatedBy().getEmployeeId() == loggedInEmployee.getEmployeeId()
							&& (auditStep.getStatus() == InternalAuditConstants.SAVED
									|| auditStep.getStatus() == InternalAuditConstants.REJECTED)) {
						auditStepView.enableFields();
					} else if (!(auditStep.getApprovedBy().getRollId() == 1) && loggedInEmployee.getRollId() == 1) {
						auditStepView.supervisorView();
					}
					if (auditStep.getStatus() == InternalAuditConstants.SUBMIT && loggedInEmployee.getRollId() != 1) {
						auditStepView.disableFields(exceptions, auditSamplingView);
					}
					if (auditStep.getStatus() == InternalAuditConstants.APPROVED
							&& auditStep.getApprovedBy().getRollId() == 1) {
						auditStepView.disableFields(exceptions, auditSamplingView);
					}
					///
					// System.out.println(loggedInEmployee.getEmployeeId());
					auditStepView.getPerformance().setText(auditStep.getProceducePerformance());
					// SamplingAuditStep s = new
					// SamplingAuditStep(auditStep.getAuditStepId() + "");
					// s.getListBoxFrequency().setItemSelected(Integer.parseInt(auditStep.getPopulation()),
					// true);
					// s.getListBoxControlList().setSelectedIndex(2);
					// s.getListBoxSamplingMethod().setSelectedIndex(2);
					// s.getTxtAreaAuditProcedure().setText("hihihihih");
					auditStepView.getPopulation().setText(auditStep.getPopulation());
					auditStepView.getSample().setText(auditStep.getSampleSelected());
					auditStepView.getSelection().setText(auditStep.getSelectionBasis());
					// this is how I set id, when needed for update
					auditStepView.getAuditStepId().setText(String.valueOf(auditStep.getAuditStepId()));

					///////////////////////////////
					fileUpload(auditStepView, auditStep);
					///

					if ("Satisfactory".equals(auditStep.getConclusion()))
						auditStepView.getConclusion().setSelectedIndex(0);
					else {
						auditStepView.getConclusion().setSelectedIndex(1);
					}
					// displayExceptions(exceptions, auditStep.getExceptions());
					// auditStepView.disableFields(exceptions);
				} else {
					auditStepView.enableFields();
				}
			}

			private void fileUpload(final AuditStepView auditStepView, final AuditStep auditStep) {
				AuditStepUploads auditStepUpload = new AuditStepUploads("0");

				auditStepUpload.getForm().addSubmitHandler(new SubmitHandler() {

					@Override
					public void onSubmit(SubmitEvent arg0) {
						saveSlectedAuditStepIdInSession(auditStep.getAuditStepId());

					}
				});

				auditStepUpload.getForm().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
					public void onSubmitComplete(SubmitCompleteEvent event) {
						if (event.getResults().contains("success")) {
							Window.alert("File uploaded");
							updateFileNameInDatabase(auditStep.getAuditStepId());

						} else {
							Window.alert(event.getResults());
						}
					}

				});
				if (auditStep.getStatus() != InternalAuditConstants.APPROVED) {
					// auditStepView.getUploadFileContainer().add(auditStepUpload);
				}

				Anchor anchorUploadedFile = new Anchor(auditStep.getUploadedFile());
				auditStepView.getUploadFileContainer().add(anchorUploadedFile);
				anchorUploadedFile.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						// Window.open("/InternalAudit/"+auditStep.getAuditStepId()+"/"+auditStep.getUploadedFile(),
						// "_blank", "");
						// Window.open(GWT.getHostPageBaseURL() +
						// "/AuditSteps//"+auditStep.getAuditStepId()+"//"+auditStep.getUploadedFile(),
						// "name", "enabled");
						Window.open("AuditSteps/" + auditStep.getAuditStepId() + "/" + auditStep.getUploadedFile(),
								"_blank", "");

					}
				});
			}
		});

	}

	private void saveSlectedAuditStepIdInSession(final int auditStepId) {
		rpcService.saveSelectedAuditStepIdInSession(auditStepId, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable arg0) {
				System.out.println("FAIL: saveSlectedAuditStepIdInSession " + auditStepId);
			}

			@Override
			public void onSuccess(String arg0) {
				System.out.println("saveSlectedAuditStepIdInSession " + auditStepId);
			}
		});
	}
	//
	// public void getSavedExceptions(final VerticalPanel exceptions, int
	// selectedJobId) {
	//
	// rpcService.getSavedExceptions( selectedJobId, new
	// AsyncCallback<ArrayList<Exceptions>>() {
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// //error with fail
	// Window.alert("Fail getting saved ex");
	//
	//
	// logger.log(Level.INFO, "FAIL: getSavedExceptions .Inside Audit
	// AuditAreaspresenter");
	// if(caught instanceof TimeOutException){
	// History.newItem("login");
	// }else{
	// System.out.println("FAIL: getSavedExceptions .Inside
	// AuditAreaspresenter");
	// Window.alert("FAIL: getSavedExceptions");// After FAIL ... write RPC Name
	// NOT Method Name..
	// }
	//
	//
	// }
	//
	// @Override
	// public void onSuccess(ArrayList<Exceptions> arg0) {
	//
	// // displayExceptions(exceptions, arg0);
	// }
	//
	//
	//
	// });
	//
	// }

	private void displayExceptions(final VerticalPanel exceptions, ArrayList<Exceptions> arg0, final AuditStepView auditStepView) {
		exceptions.clear();
//		alreadySavedExceptions = arg0;
//		alreadySavedExceptions = new ArrayList<ExceptionRow>();
		auditStepView.getListExceptionUploads().clear();
		auditStepView.getArrayListExceptions().clear();
		for (final Exceptions exception : arg0) {

			final ExceptionRow row = new ExceptionRow(exception.getExceptionId());// employee
			row.getExId().setText(String.valueOf(exception.getExceptionId()));
			row.getException().setText(String.valueOf(exception.getDetail()));
			auditStepView.getArrayListExceptions().add(row);
			try {
				row.getAddObservationUpload().fetchProcedureAttachments(String.valueOf(exception.getExceptionId()), "SamplingExceptionUploads");
				auditStepView.getListExceptionUploads().add(row.getAddObservationUpload());
//				if(row.getAddObservationUpload().getLblfilename() == null ) {
//					row.getAddObservationUpload().setAttachedFile(true);
//				}
//				alreadySavedExceptions.add(row);
			}
			catch (Exception ex) {
//				Window.alert("Exception occuered in already atttached observation");
				// TODO: handle exception
			}
			exceptions.add(row);
			final DataSetter dataSetter = new DataSetter();
			dataSetter.setId(exception.getExceptionId());

			// Remove Saved Row...
			row.getRemoveRow().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (Window.confirm("Are you sure you want to remove this Exception ?")) {
						row.removeRow();
//						alreadySavedExceptions.remove(row);
						exceptions.remove(row);
						deleteException(dataSetter.getId());
						row.getAddObservationUpload().removeAttachedFile(String.valueOf(exception.getExceptionId()), "SamplingExceptionUploads", null);
						removeException(auditStepView, row); 
					}
				}

			});
			//

		}
	}

	private void deleteException(int id) {
		rpcService.deleteException(id, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Exception in deleteException");
			}

			@Override
			public void onSuccess(String result) {
				new DisplayAlert("Exception removed");
			}
		});
	}
	
	private void removeException(AuditStepView auditStepView, ExceptionRow row) {
		auditStepView.getListExceptionUploads().remove(row.getAddObservationUpload());
		auditStepView.getArrayListExceptions().remove(row);
	} 

}
