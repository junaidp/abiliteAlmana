package com.internalaudit.client.view.AuditEngagement;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.util.DataStorage;
import com.internalaudit.client.util.MyUtil;
import com.internalaudit.client.view.DisplayAlert;
import com.internalaudit.client.view.PopupsView;
import com.internalaudit.client.widgets.AddImage;
import com.internalaudit.shared.ActivityObjective;
import com.internalaudit.shared.AuditEngagement;
import com.internalaudit.shared.AuditProgramme;
import com.internalaudit.shared.AuditWork;
import com.internalaudit.shared.Employee;
import com.internalaudit.shared.InternalAuditConstants;
import com.internalaudit.shared.JobCreation;
import com.internalaudit.shared.RiskObjective;
import com.internalaudit.shared.SubProcess;
import com.internalaudit.shared.SuggestedControls;
import com.internalaudit.shared.TimeOutException;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

public class KickoffView extends Composite {

	private Logger logger = Logger.getLogger("KickoffView");

	private static KickoffViewUiBinder uiBinder = GWT.create(KickoffViewUiBinder.class);

	interface KickoffViewUiBinder extends UiBinder<Widget, KickoffView> {
	}

	@UiField
	VerticalPanel statusPanel;

	@UiField
	VerticalPanel exPanel;

	@UiField
	Label jobName;

	@UiField
	Label startDate;

	@UiField
	Label endDate;

	@UiField
	Label lblProcess;

	@UiField
	TextArea lblAuditableUnit;

	@UiField
	Label lblSubProcess;
	
	@UiField
	Label lblJobType;
	
	@UiField
	VerticalPanel vpnlSubProcess;
	private String userPackage;
	private Employee loggedInUser;
	private ContentPanel panel;
	private InternalAuditServiceAsync rpcService;
	int selectedJobId = 0;
	private int auditEngId = 0;
	private AuditEngagement selectedAuditEngagement;
	private SubProcess subProcess;
	// private ArrayList<SubProcess> listSubProcess;

	public KickoffView(InternalAuditServiceAsync rpcService, int selectedjobId, int auditEngId, Employee loggedInUser,
			AuditEngagement auditEngagement) {
		this.selectedAuditEngagement = auditEngagement;

		this.rpcService = rpcService;
		this.loggedInUser = loggedInUser;
		this.selectedJobId = selectedjobId;
		initWidget(uiBinder.createAndBindUi(this));
		this.auditEngId = auditEngId;
		lblAuditableUnit.setEnabled(false);
		jobName.setWordWrap(false);
		fetchCreatedJob(selectedJobId, loggedInUser);

		updateKickoffStatus(selectedjobId);
		// getRiskInfo( auditEngId );

		getJobRelatedInfo(selectedjobId);

		getExceptions();
		// added button click just to check test the view
		fetchCompanyPackage(loggedInUser.getCompanyId());
	}

	private void updateKickoffStatus(int auditEngId) {

		rpcService.updateKickoffStatus(auditEngId, loggedInUser, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail");

				logger.log(Level.INFO, "FAIL: updateKickoffStatus .Inside Audit AuditAreaspresenter");
				if (caught instanceof TimeOutException) {
					History.newItem("login");
				} else {
					System.out.println("FAIL: updateKickoffStatus .Inside AuditAreaspresenter");
					Window.alert("FAIL: updateKickoffStatus");// After FAIL ...
																// write RPC
																// Name NOT
																// Method Name..
				}

			}
		});

	}

	int jobid;

	private void getJobRelatedInfo(int selectedjobId) {

		rpcService.fetchCreatedJob(selectedjobId, new AsyncCallback<JobCreation>() {

			@Override
			public void onSuccess(JobCreation result) {

				if (result != null) {

					jobid = result.getJobCreationId();
					startDate.setText(result.getStartDate() == null ? "" : result.getStartDate());
					endDate.setText(result.getEndDate() == null ? "" : result.getEndDate());
					jobName.setText(result.getJobName() == null ? "" : result.getJobName());
					lblProcess.setText(result.getStrategic().getProcess() == null ? ""
							: result.getStrategic().getProcess().getProcessName());
					// lblSubProcess.setText(result.getStrategic().getSubProcess()
					// == null ? ""
					// :
					// result.getStrategic().getSubProcess().getSubProcessName());
					// if multiple SubProcess selected
					if (result.getStrategic().getListSubProcess().size() > 0) {
						vpnlSubProcess.clear();
						for (int i = 0; i < result.getStrategic().getListSubProcess().size(); i++) {
							Label lblSubProces = new Label(
									result.getStrategic().getListSubProcess().get(i).getSubProcessName());
							lblSubProces.setWidth("100%");
							vpnlSubProcess.add(lblSubProces);
						}
					}

					lblJobType.setText(result.getStrategic().getJobType() == null ? ""
							: result.getStrategic().getJobType().getJobTypeName());
					lblAuditableUnit.setText(result.getStrategic().getAuditableUnit() == null ? ""
							: result.getStrategic().getAuditableUnit());

					// TODO: NEED TO BE ADJUSTED FROM LIST OF SUBPROCESS
					subProcess = result.getStrategic().getListSubProcess().get(0);
					// subProcess = result.getStrategic().getSubProcess();
					//
					// listSubProcess.addAll(result.getStrategic().getListSubProcess());
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Rpc Failed: fetchCreatedJob in Kick off View");

				logger.log(Level.INFO, "FAIL: fetchCreatedJob .Inside Audit AuditAreaspresenter");
				if (caught instanceof TimeOutException) {
					History.newItem("login");
				} else {
					System.out.println("FAIL: fetchCreatedJob .Inside AuditAreaspresenter");
					Window.alert("FAIL: fetchCreatedJob");// After FAIL ...
															// write RPC Name
															// NOT Method Name..
				}

			}
		});

	}

	private void getExceptions() {

		// rpcService.fetchJobExceptions(selectedJobId, new
		// AsyncCallback<ArrayList<Exceptions>>() {
		//
		// @Override
		// public void onSuccess(ArrayList<Exceptions> ex) {
		//
		// for ( int i = 0; i < ex.size(); i++)
		// {
		// System.out.println( ex.get(i).getDetail());
		//
		// exPanel.add(new Label(ex.get(i).getDetail()));
		// exPanel.add(new Label(ex.get(i).getJobName()));
		// }
		//
		// }
		//
		// @Override
		// public void onFailure(Throwable arg0) {
		//
		// }
		// });

	}

	public void fetchCreatedJob(int selectedJobId, final Employee loggedInUser) {

		rpcService.fetchAuditEngagement(selectedJobId, new AsyncCallback<AuditEngagement>() {

			@Override
			public void onFailure(Throwable caught) {

				logger.log(Level.INFO, "FAIL: fetchAuditEngagement .Inside Audit AuditAreaspresenter");
				if (caught instanceof TimeOutException) {
					History.newItem("login");
				} else {
					System.out.println("FAIL: fetchAuditEngagement .Inside AuditAreaspresenter");
					Window.alert("FAIL: fetchAuditEngagement");// After FAIL ...
																// write RPC
																// Name NOT
																// Method Name..
				}

			}

			@Override
			public void onSuccess(AuditEngagement record) {
				showOptionsAccordian(record);

			}
		});
	}

	private void showOptionsAccordian(final AuditEngagement record) { 
		panel = new ContentPanel();
		panel.setHeaderVisible(false);

		panel.setWidth("1205px");

		// panel.setHeadingText("Audit Planning");

		panel.setBodyBorder(false);
		final AccordionLayoutContainer con = new AccordionLayoutContainer();
		panel.add(con);

		final AccordionLayoutAppearance appearance = GWT
				.<AccordionLayoutAppearance>create(AccordionLayoutAppearance.class);

		///////////////////////////////////// AUDIT NOTIFICATION
		///////////////////////////////////// ///////////////////////////
		ContentPanel cp = new ContentPanel(appearance);

		cp.setAnimCollapse(false);
		cp.setHeadingText("Audit Notification");

		VerticalPanel vpnlIdentification = new VerticalPanel();
		vpnlIdentification.setHeight("400px");

		AuditNotificationViewNew auditNotificationViewNew = new AuditNotificationViewNew(record, refreshMethod(con));
		// AuditNotificationView auditNotificationView = new
		// AuditNotificationView();
		// vpnlIdentification.add(auditNotificationView);
		// auditNotificationView.getAuditNotificationViewData().setData(auditNotificationView,
		// rpcService, selectedAuditEngagement);

		vpnlIdentification.add(auditNotificationViewNew);
		// if (!record.getSubject().isEmpty()) {
		auditNotificationViewNew.getAuditNotificationViewNewData().setData(auditNotificationViewNew, rpcService,
				record);
		// }
		cp.add(vpnlIdentification);
		con.add(cp);

		///////////////////////////////////// Client Kickoff Meeting Objectives
		///////////////////////////////////// ///////////////////////////

		objectivesLayout(record, con, appearance);

		///////////////////////////////////// KEY RISKS
		///////////////////////////////////// ///////////////////////////

		keyRisksLayout(record, con, appearance);

		///////////////////////////////////// Objective Risk Control
		///////////////////////////////////// Matrix///////////////////////////

		objectiveRiskControlMatrix(record, con, appearance);

		//////////////////////////////////////// Audit Work
		//////////////////////////////////////// Programme//////////////////////////////

		auditWorkProgram(record, panel, con, appearance);

	}

	private void auditWorkProgram(final AuditEngagement record, ContentPanel panel, final AccordionLayoutContainer con,
			AccordionLayoutAppearance appearance) {
		ContentPanel cp;
		cp = new ContentPanel(appearance);
		cp.setAnimCollapse(false);
		cp.setBodyStyleName("pad-text");
		cp.setHeadingText("Audit Work Programme");
		VerticalPanel vpnl = new VerticalPanel();
		// vpnl.setHeight("400px");
		//Button btnLibrary = new Button("Library");
		//vpnl.add(btnLibrary);
		final VerticalPanel vpnlPopup = new VerticalPanel();
		//final ScrollPanel scrollPopup = new ScrollPanel();
		//scrollPopup.add(vpnlPopup);
		//scrollPopup.setHeight("530px");
		//vpnlPopup.setHeight("400px");
		
//		btnLibrary.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				if(record.getEngagementDTO().getAuditProgrammeList().size()<1)
//					new DisplayAlert("No Library added");
//				else {
//				final PopupsView popUp = new PopupsView(scrollPopup, "Audit Work Program Library");
//				Button btnClose = new Button("Close");
//				popUp.getVpnlMain().add(btnClose);
//				btnClose.getElement().getStyle().setMarginLeft(530, Unit.PX);
//				btnClose.addClickHandler(new ClickHandler() {
//
//					@Override
//					public void onClick(ClickEvent event) {
//						// TODO Auto-generated method stub
//						popUp.getVpnlMain().removeFromParent();
//						popUp.getPopup().removeFromParent();
//					}
//				});
//				}
//			}
//		});

		final VerticalPanel auditWorkNewContainer = new VerticalPanel();
		final AuditWorkProg auditWorkProg = new AuditWorkProg(rpcService, selectedJobId, loggedInUser, userPackage, record.getEngagementDTO().getSelectedControls(), auditWorkNewContainer, refreshMethod(con), vpnlPopup, record.getEngagementDTO().getAuditProgrammeList().size());
		vpnl.add(auditWorkProg);

		// AddIcon btnAddAuditWork = new AddIcon();
		// AddImage btnAddAuditWork = new AddImage();
		// btnAddAuditWork.addStyleName("w3-right");
		Button btnSaveAuditWork = new Button("Save");
		Button btnApproveAuditWork = new Button("Approve");
		btnSaveAuditWork.addStyleName("w3-margin");
		btnApproveAuditWork.addStyleName("w3-margin");

		// verticalPanelAuditWorkProg.add(new AuditWorkProgramNew());
		// verticalPanelAuditWorkProg.add(addpanelAuditworkprog);
		// 2018

		// library
		for (AuditProgramme auditParogramsLibrary : record.getEngagementDTO().getAuditProgrammeList()) {
			boolean isAddInLibrary = true;
			if(record.getEngagementDTO().getSelectedAuditWorkforPrograms().size()>1 || record.getEngagementDTO().getSelectedAuditWorkforPrograms() != null)
			for(AuditWork selectedControls : record.getEngagementDTO().getSelectedAuditWorkforPrograms()) {
			if(auditParogramsLibrary.getAuditProgrammeName().equals(selectedControls.getDescription()))
				isAddInLibrary = false;
			}
			if(isAddInLibrary)
				viewAuditParogramsLibrary(vpnl, vpnlPopup, auditWorkNewContainer, auditWorkProg, auditParogramsLibrary);
		} 
		// addclickhandler of button risk
		// btnAddAuditWork.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// AuditWorkProgramNew auditWork = new AuditWorkProgramNew();
		// auditWork.getLblReferenceData().setText(MyUtil.getRandom());
		// auditWork.hideElemetns();
		// auditWorkNewContainer.add(auditWork);
		//
		// }
		// });

		btnSaveAuditWork.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ArrayList<AuditProgramme> auditWorkProgrammes = new ArrayList<AuditProgramme>();
				for (int i = 0; i < auditWorkNewContainer.getWidgetCount(); i++) {
					AuditWorkProgramNew auditWorkProgramView = (AuditWorkProgramNew) auditWorkNewContainer.getWidget(i);
					AuditProgramme auditProgramme = new AuditProgramme();
					auditWorkProgramView.getData(auditProgramme);

					auditWorkProgrammes.add(auditProgramme);
				}
				saveAuditWorkProgramme(auditWorkProgrammes, con);

			}

		});
//		HorizontalPanel panelAuditWorkBtn = new HorizontalPanel();
//		panelAuditWorkBtn.addStyleName("w3-display-bottom");
//		panelAuditWorkBtn.add(btnSaveAuditWork);
//		panelAuditWorkBtn.add(btnApproveAuditWork);
//		panelAuditWorkBtn.setVisible(false);
		VerticalLayoutContainer scroll = new VerticalLayoutContainer();
		if (record.getEngagementDTO().getAuditProgrammeList().isEmpty()
				&& record.getEngagementDTO().getSelectedControls().isEmpty()) {
			// changedvpn1
			auditWorkNewContainer.clear();
		}
		if(record.getEngagementDTO().getStatusControlRisk() != InternalAuditConstants.INITIATED && record.getEngagementDTO().getStatusControlRisk() != InternalAuditConstants.APPROVED) {
			scroll.setVisible(false);
			vpnl.setVisible(false);
		}
		//scroll.setWidget(vpnl);
		scroll.add(vpnl);
		scroll.setHeight("400px");
		scroll.setScrollMode(ScrollMode.AUTOY);

		// vpnl.add(new AuditWorkProgramNew());
		cp.add(scroll);
		con.add(cp);
		////////////////////////////////////////////// AUDIT STEP
		cp = new ContentPanel(appearance);
		cp.setAnimCollapse(false);
		cp.setBodyStyleName("pad-text");
		cp.setHeadingText("Audit Step");
		VerticalLayoutContainer sp = new VerticalLayoutContainer();
		sp.add(new AuditStepContainer(selectedJobId, rpcService, loggedInUser));
		sp.setHeight("500px");
		sp.setScrollMode(ScrollMode.AUTOY);
		cp.add(sp);
		con.add(cp);
		statusPanel.add(panel);
	}

	private void viewAuditParogramsLibrary(VerticalPanel vpnl, final VerticalPanel vpnlPopup,
			final VerticalPanel auditWorkNewContainer, final AuditWorkProg auditWorkProg,
			AuditProgramme auditParogramsLibrary) {
		final AuditWorkProgramNew auditWorkProgramNew = new AuditWorkProgramNew();
		auditWorkProgramNew.setPopupView();
		vpnlPopup.add(auditWorkProgramNew);
		vpnl.add(auditWorkNewContainer);
		auditWorkProgramNew.setData(auditParogramsLibrary);
		auditWorkProgramNew.getBtnSelect().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				auditWorkProg.addRow(auditWorkProgramNew);
				auditWorkProgramNew.getBtnSelect().setVisible(false);
			}
		});
	}

	private void objectiveRiskControlMatrix(final AuditEngagement record, final AccordionLayoutContainer con,
			AccordionLayoutAppearance appearance) {
		ContentPanel cp;
		cp = new ContentPanel(appearance);
		cp.setAnimCollapse(false);

		cp.setBodyStyleName("pad-text");
		cp.setHeadingText("Objective Risk Control Matrix");

		// ScrollPanel vps = new ScrollPanel();
		// vps.setHeight("400px");
		VerticalPanel vpExistingControl = new VerticalPanel();
		final VerticalPanel vpExistingControlContainer = new VerticalPanel();
//		final ScrollPanel scrollExistingControlContainer = new ScrollPanel();
//		scrollExistingControlContainer.setHeight("530px");
//		scrollExistingControlContainer.add(vpExistingControlContainer);
		final VerticalPanel userRiskControlContainer = new VerticalPanel();

		// library's
//		Button btnLibrary = new Button("Library");
//		btnLibrary.setWidth("100px");
		//vpExistingControl.add(btnLibrary);
		
		// user's
		final RisksView riskView = new RisksView(auditEngId, rpcService, loggedInUser, userPackage,
						record.getEngagementDTO().getSelectedObjectiveRisks(), vpExistingControlContainer, refreshMethod(con), record.getEngagementDTO().getSuggestedControlsList().size());
			userRiskControlContainer.add(riskView);
			riskView.showhideSaveSubmitButtons(false); 

		ArrayList<Integer> riskIds = new ArrayList<Integer>();
		//check is added below by moqeet, selected objectivs not added again in library
		for (SuggestedControls suggestedControlsLibrary : record.getEngagementDTO().getSuggestedControlsList()) {
			boolean isAddInLibrary = true;
			if(record.getEngagementDTO().getSelectedControls().size() > 0 || record.getEngagementDTO().getSelectedActivityObjectives() != null)
			for(SuggestedControls objectiveControlsSelected : record.getEngagementDTO().getSelectedControls())
			if(suggestedControlsLibrary.getSuggestedControlsName().equals(objectiveControlsSelected.getSuggestedControlsName()))
				isAddInLibrary = false;
			if(isAddInLibrary)
				viewLibraryObjectiveRiskControlMatrix(vpExistingControlContainer, riskView, riskIds, suggestedControlsLibrary);
		}

		HorizontalPanel panelButtons = new HorizontalPanel();
		// panelButtons.getElement().getStyle().setMarginTop(10, Unit.PX);
		// a panelButtons.getElement().getStyle().setMarginLeft(1100, Unit.PX);
		panelButtons.addStyleName("w3-display-bottom w3-margin");
		// final VerticalPanel addPanelExistingControl = new VerticalPanel();
		// vpExistingControl.add(addPanelExistingControl);
		vpExistingControl.add(userRiskControlContainer);
		// Label library = new Label("---------Library----------");
		// library.addStyleName("libraryText");
		// vpExistingControlContainer.add(new
		// Label("---------Library----------"));
//		btnLibrary.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				if(record.getEngagementDTO().getSuggestedControlsList().size()<1)
//					new DisplayAlert("No Library added");
//				else {
//				final PopupsView popUp = new PopupsView(scrollExistingControlContainer, "User Risk Library");
//				Button btnClose = new Button("Close");
//				popUp.getVpnlMain().add(btnClose);
//				btnClose.getElement().getStyle().setMarginLeft(755, Unit.PX);
//				btnClose.addClickHandler(new ClickHandler() {
//
//					@Override
//					public void onClick(ClickEvent event) {
//						// TODO Auto-generated method stub
//						popUp.getVpnlMain().removeFromParent();
//						popUp.getPopup().removeFromParent();
//						
//					}
//				});
//				}
//			}
//		});
		// vpExistingControl.add(vpExistingControlContainer);
		// vps.add(vpExistingControl);
		vpExistingControl.setHeight("400px");

		if (record.getEngagementDTO().getSuggestedControlsList().isEmpty()
				&& record.getEngagementDTO().getSelectedObjectiveRisks().isEmpty()) {
			vpExistingControlContainer.clear();
		}
		//When user directly add and submit Audit work in Audit Work Programme, this tab invisible
		if(record.getEngagementDTO().getSelectedAuditWorkforPrograms().size()>0 && record.getEngagementDTO().getSelectedControls().size()<1) {
			if(record.getEngagementDTO().getSelectedAuditWorkforPrograms().get(0).getStatus() == InternalAuditConstants.SUBMIT 
					|| record.getEngagementDTO().getSelectedAuditWorkforPrograms().get(0).getStatus() == InternalAuditConstants.APPROVED )			
			vpExistingControl.setVisible(false);
		}


		// cp.add(panelAdd);
		cp.add(vpExistingControl);

		con.add(cp); 
		/*
		 * btnSaveControl.addClickHandler(new ClickHandler(){
		 * 
		 * @Override public void onClick(ClickEvent event) {
		 * ArrayList<SuggestedControls> suggestedControls = new
		 * ArrayList<SuggestedControls>(); for(int i=0; i<
		 * userRiskControlContainer.getWidgetCount() ; i++){
		 * RiskControlMatrixView existingControlView =
		 * (RiskControlMatrixView)userRiskControlContainer.getWidget(i);
		 * SuggestedControls suggestedControl = new SuggestedControls();
		 * existingControlView.getData(suggestedControl);
		 * suggestedControls.add(suggestedControl); }
		 * saveExistingControls(suggestedControls);
		 * 
		 * }
		 * 
		 * 
		 * });
		 */
	}

	private void viewLibraryObjectiveRiskControlMatrix(final VerticalPanel vpExistingControlContainer,
			final RisksView riskView, ArrayList<Integer> riskIds, SuggestedControls suggestedControlsLibrary) {
		final RiskControlMatrixView riskControlMatrixView = new RiskControlMatrixView();
		riskControlMatrixView.setPopupView();
		vpExistingControlContainer.add(riskControlMatrixView);
		final RiskObjective riskObjective = suggestedControlsLibrary.getRiskId();
		boolean riskAdded = riskIds.contains(riskObjective.getRiskId());
		riskIds.add(riskObjective.getRiskId());
		riskControlMatrixView.setData(suggestedControlsLibrary, riskAdded);
		riskControlMatrixView.setRiskObjective(riskObjective);

		riskControlMatrixView.getBtnSelect().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				riskControlMatrixView.getBtnSelect().setVisible(false);
				riskView.addRow(riskControlMatrixView, riskObjective);
				riskView.showhideSaveSubmitButtons(true);
			}
		});
	}

	private void keyRisksLayout(final AuditEngagement record, final AccordionLayoutContainer con,
			AccordionLayoutAppearance appearance) {

		ContentPanel cp;
		cp = new ContentPanel(appearance);
		cp.setAnimCollapse(false);
		cp.setBodyStyleName("pad-text");
		cp.setHeadingText("Key Risks");
		VerticalLayoutContainer scrollMainKeyRisks = new VerticalLayoutContainer();
		scrollMainKeyRisks.setScrollMode(ScrollMode.AUTOY);
		// v.setWidth("600px");
		VerticalPanel verticalPanelKeyRisks = new VerticalPanel();
		final VerticalPanel verticalPanelKeyRisksContainer = new VerticalPanel();
		final VerticalLayoutContainer scrollKeyRisksContainer = new VerticalLayoutContainer();
		scrollKeyRisksContainer.setHeight("530px");
		scrollKeyRisksContainer.setScrollMode(ScrollMode.AUTOY);
		scrollKeyRisksContainer.add(verticalPanelKeyRisksContainer);
		final VerticalPanel usersRisksContainer = new VerticalPanel();
		Button btnSaveKeyRisk = new Button("Save");
		Button btnSubmitKeyRisk = new Button("Submit");
		Button btnLibraryKeyRisk = new Button("Library");
		btnLibraryKeyRisk.setWidth("100px");
		
		// AddIcon btnAdd = new AddIcon();
		AddImage btnAdd = new AddImage();
		final HorizontalPanel hpnlButton = new HorizontalPanel();
		hpnlButton.add(btnSaveKeyRisk);
		hpnlButton.add(btnSubmitKeyRisk);
		hpnlButton.getElement().getStyle().setMarginLeft(1010, Unit.PX);
		hpnlButton.getElement().getStyle().setMarginTop(10, Unit.PX);
		hpnlButton.setVisible(false);

		HorizontalPanel hpnlTopAdd = new HorizontalPanel();
		verticalPanelKeyRisks.add(hpnlTopAdd);
		hpnlTopAdd.getElement().getStyle().setMarginTop(5, Unit.PX);
		
		// User's LIBRARY Selected
		for (int j = 0; j < record.getEngagementDTO().getSelectedObjectiveRisks().size(); j++) {
			final KeyRiskViewNew keyRiskView = new KeyRiskViewNew();
			keyRiskView.usersView();
			hpnlButton.setVisible(true);
			final RiskObjective objectiveRisk = record.getEngagementDTO().getSelectedObjectiveRisks().get(j);
			keyRiskView.setData(objectiveRisk);
			if ((record.getEngagementDTO().getSelectedObjectiveRisks().get(j).getStatus() == InternalAuditConstants.SUBMIT)
					&& !record.getEngagementDTO().isRiskControlFeedback()) {
				keyRiskView.disable();
				hpnlButton.setVisible(false);
				btnAdd.setVisible(false);
			}
			

//			if(record.getEngagementDTO().getStatusControlRisk() == InternalAuditConstants.REJECTED)
//				btnSubmitKeyRisk.setVisible(false);
			
			keyRiskView.getDelete().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					boolean confirmed = Window.confirm("Are you sure you want to delete this risk?");

					if (confirmed) {
						keyRiskView.removeFromParent();

						deleteObjectiveRisk(objectiveRisk.getRiskId());
					}
				}
			});

			usersRisksContainer.add(keyRiskView);
		}

		// ArrayList<Integer> objectiveIds = new ArrayList<Integer>();

		// library

		if(record.getEngagementDTO().getStatusControlRisk() == InternalAuditConstants.REJECTED) {
			hpnlTopAdd.add(btnLibraryKeyRisk);
			hpnlButton.setVisible(true);
		}
			
		if (record.getEngagementDTO().getSelectedObjectiveRisks().size() <= 0 || record.getEngagementDTO().getSelectedObjectiveRisks().get(0).getStatus() == InternalAuditConstants.SAVED) {
			hpnlTopAdd.add(btnLibraryKeyRisk);

			for (RiskObjective riskObjectivesLibrary : record.getEngagementDTO().getRiskObjectiveList()) {
				boolean isAddInLibrary = true;
				if(record.getEngagementDTO().getSelectedObjectiveRisks().size() > 0 || record.getEngagementDTO().getSelectedObjectiveRisks() != null)
				for(RiskObjective riskObjectivesLibrarySelected : record.getEngagementDTO().getSelectedObjectiveRisks()) {
				if(riskObjectivesLibrary.getRiskname().equals(riskObjectivesLibrarySelected.getRiskname()))
					isAddInLibrary = false;
				//this check is added by moqeet,already selected not added in library again.
				}
				if(isAddInLibrary)
				viewLibraryKeyRisks(verticalPanelKeyRisksContainer, usersRisksContainer, hpnlButton, riskObjectivesLibrary);
			}
		}
  
		if(userPackage.equalsIgnoreCase("Basic") || userPackage.equalsIgnoreCase("Gold")) {
			btnLibraryKeyRisk.setVisible(false); 
			btnAdd.getElement().getStyle().setMarginLeft(1150, Unit.PX);
		  }
		  else
			  btnAdd.getElement().getStyle().setMarginLeft(1050, Unit.PX);
		
		btnAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hpnlButton.setVisible(true);
				final KeyRiskViewNew keyRiskViewNew = new KeyRiskViewNew();
				keyRiskViewNew.usersView();
				keyRiskViewNew.getLblRef().setText(MyUtil.getRandom());
				keyRiskViewNew.populateObjectives(record.getEngagementDTO().getSelectedActivityObjectives());
				keyRiskViewNew.addStyleName("w3-sand");
				usersRisksContainer.add(keyRiskViewNew);

				keyRiskViewNew.getDelete().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						keyRiskViewNew.removeFromParent();
						if(usersRisksContainer.getWidgetCount()<1)
							hpnlButton.setVisible(false);
					}
				});

			}
		});
		hpnlTopAdd.add(btnAdd); 
		verticalPanelKeyRisks.add(usersRisksContainer);
		btnLibraryKeyRisk.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(verticalPanelKeyRisksContainer.getWidgetCount()<1)
					new DisplayAlert("No Library added");
				else {
				final PopupsView popUp = new PopupsView(scrollKeyRisksContainer, "Key Risk Library");
//				Button btnClose = new Button("Close");
//				popUp.getVpnlMain().add(btnClose);
//				btnClose.getElement().getStyle().setMarginLeft(590, Unit.PX);
//				btnClose.addClickHandler(new ClickHandler() {
//
//					@Override
//					public void onClick(ClickEvent event) {
//						// TODO Auto-generated method stub
//						popUp.getVpnlMain().removeFromParent();
//						popUp.getPopup().removeFromParent();
//					}
//				});
				}
			}
		});
		// verticalPanelKeyRisks.add(verticalPanelKeyRisksContainer);
		verticalPanelKeyRisks.add(hpnlButton);

		// hpnlButton.getElement().getStyle().setMarginTop(30, Unit.PX);
		// hpnlButton.addStyleName("w3-display-bottom w3-margin");

		if (record.getEngagementDTO().getRiskObjectiveList().isEmpty()
				&& record.getEngagementDTO().getSelectedObjectiveRisks().isEmpty()) {
			verticalPanelKeyRisksContainer.clear();//////// here
		}
		//When user directly add and submit Audit work in Audit Work Programme, this tab invisible
		if(record.getEngagementDTO().getSelectedAuditWorkforPrograms().size()>0 && record.getEngagementDTO().getSelectedObjectiveRisks().size()<1) {
			if(record.getEngagementDTO().getSelectedAuditWorkforPrograms().get(0).getStatus() == InternalAuditConstants.SUBMIT 
					|| record.getEngagementDTO().getSelectedAuditWorkforPrograms().get(0).getStatus() == InternalAuditConstants.APPROVED )			
			scrollMainKeyRisks.setVisible(false);
		}
		scrollMainKeyRisks.setHeight("400px");
		scrollMainKeyRisks.add(verticalPanelKeyRisks);
		cp.add(scrollMainKeyRisks);
		con.add(cp);

		btnSaveKeyRisk.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setkeyRisksToSave(con, usersRisksContainer, InternalAuditConstants.SAVED);
			}
		});

		btnSubmitKeyRisk.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setkeyRisksToSave(con, usersRisksContainer, InternalAuditConstants.SUBMIT);
			}
		});
	}
	
	private boolean txtRiskValidation(VerticalPanel usersRisksContainer) {
		boolean flag = false;
		for (int i = 0; i < usersRisksContainer.getWidgetCount(); i++) {
			KeyRiskViewNew keyRiskView = (KeyRiskViewNew) usersRisksContainer.getWidget(i);
			if(keyRiskView.getTxtRisk().getText().length() > 0)
				flag = true;
			else
				flag = false;
		}
		return flag;
	}

	private void setkeyRisksToSave(final AccordionLayoutContainer con, final VerticalPanel usersRisksContainer, int action) {
		if(txtRiskValidation(usersRisksContainer)) {
			ArrayList<RiskObjective> riskObjectives = new ArrayList<RiskObjective>();
			for (int i = 0; i < usersRisksContainer.getWidgetCount(); i++) {
				KeyRiskViewNew keyRiskView = (KeyRiskViewNew) usersRisksContainer.getWidget(i);
				RiskObjective riskObjective = new RiskObjective();
				keyRiskView.getData(riskObjective);
				riskObjectives.add(riskObjective);
			}
			if(action == InternalAuditConstants.SUBMIT) {
				boolean confirm =  Window.confirm("Do you want to submit the \"Key Risks\"\n" + 
						"Note: After submission you will not be able to make changes.");
				if(confirm) {
					saveRiskObjectives(riskObjectives, InternalAuditConstants.SUBMIT, con);
				}
			}
			else
				saveRiskObjectives(riskObjectives, InternalAuditConstants.SAVED, con);
		}
		else
			Window.alert("Enter some text in 'Risk'");
	}

	private void viewLibraryKeyRisks(final VerticalPanel verticalPanelKeyRisksContainer,
			final VerticalPanel usersRisksContainer, final HorizontalPanel hpnlButton,
			RiskObjective riskObjectivesLibrary) {
		final KeyRiskViewNew keyRiskView = new KeyRiskViewNew();
		keyRiskView.setPopupView();
		verticalPanelKeyRisksContainer.add(keyRiskView);
		final RiskObjective riskObjective = riskObjectivesLibrary;
		keyRiskView.setData(riskObjective);
		//final DataStorage dataStorage = new DataStorage(); //DataStorage, not using
// We will use the same datastoage class and set the same count and value field for every other tabs too.
		//dataStorage.setCount(i);
		keyRiskView.getBtnSelect().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hpnlButton.setVisible(true);
				keyRiskView.getBtnSelect().setVisible(false);
				final KeyRiskViewNew keyRiskSelectedView = new KeyRiskViewNew();
				keyRiskSelectedView.usersView();
				keyRiskView.getData(keyRiskSelectedView);
				keyRiskSelectedView.addStyleName("w3-sand");
				usersRisksContainer.add(keyRiskSelectedView);
				keyRiskSelectedView.getDelete().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						keyRiskSelectedView.removeFromParent();
						keyRiskView.getBtnSelect().setVisible(true);
						if(usersRisksContainer.getWidgetCount()<1)
							hpnlButton.setVisible(false);
					}
				});

			}
		});
	}

	protected void deleteObjectiveRisk(int riskId) {
		rpcService.deleteRiskObjective(riskId, jobid, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail deleting risk " + caught.getLocalizedMessage());
			}
		});

	}

	private void objectivesLayout(final AuditEngagement record, final AccordionLayoutContainer con,
			final AccordionLayoutAppearance appearance) {
		final ContentPanel cp;

		cp = new ContentPanel(appearance);
		cp.setAnimCollapse(false);
		cp.setBodyStyleName("pad-text");
		cp.setHeadingText("Objectives");

		final VerticalPanel vpnlActicityObjective = new VerticalPanel();
		final VerticalPanel vpnlActicityObjectiveContainer = new VerticalPanel();
		final VerticalLayoutContainer scrollActicityObjectiveContainer = new VerticalLayoutContainer();
		scrollActicityObjectiveContainer.setSize("100%", "530px");
		scrollActicityObjectiveContainer.setScrollMode(ScrollMode.AUTOY);
		scrollActicityObjectiveContainer.add(vpnlActicityObjectiveContainer);
		final VerticalPanel usersActivityContainer = new VerticalPanel();
		Button btnSaveActicityObjective = new Button("Save");
		Button btnSubmitActicityObjective = new Button("Submit");
		// btnSaveActicityObjective.addStyleName("w3-display-bottom w3-margin");
		// btnSaveActicityObjective.getElement().getStyle().setMarginLeft(600,
		// Unit.PX);
		vpnlActicityObjective.setHeight("370px");

		AddImage btnAddAcitivityObjective = new AddImage();

		final HorizontalPanel hpnlButtons = new HorizontalPanel();
		hpnlButtons.add(btnSaveActicityObjective);
		hpnlButtons.add(btnSubmitActicityObjective);
		hpnlButtons.getElement().getStyle().setMarginLeft(1010, Unit.PX);
		hpnlButtons.setVisible(false);

		// User's LIBRARY
		for (int j = 0; j < record.getEngagementDTO().getSelectedActivityObjectives().size(); j++) {
			final ActivityObjectiveViewNew activityObjectiveView = new ActivityObjectiveViewNew();
			activityObjectiveView.getBtnSelectActivity().setVisible(false);
			activityObjectiveView.getDelete().setVisible(true);
			hpnlButtons.setVisible(true);
			activityObjectiveView.setData(record.getEngagementDTO().getSelectedActivityObjectives().get(j));
			if(record.getEngagementDTO().getSelectedActivityObjectives() != null && (!record.getEngagementDTO().getSelectedActivityObjectives().isEmpty())) {	
//				boolean confirm = record.getEngagementDTO().getSelectedActivityObjectives().get(j).getStatus() == InternalAuditConstants.SUBMIT;
				if (record.getEngagementDTO().getSelectedActivityObjectives().get(j).getStatus() == InternalAuditConstants.SUBMIT &&
						!record.getEngagementDTO().isRiskControlFeedback()) {
					activityObjectiveView.disable();
					btnSaveActicityObjective.setVisible(false);
					btnAddAcitivityObjective.setVisible(false);
					btnSubmitActicityObjective.setVisible(false);
				}
		}
//			if(record.getEngagementDTO().getStatusControlRisk() == InternalAuditConstants.REJECTED)
//				btnSubmitActicityObjective.setVisible(false);
//			if(record.getEngagementDTO().getSelectedObjectiveRisks().size() > 0) {
//				if(record.getEngagementDTO().getSelectedObjectiveRisks().get(j).getStatus() == InternalAuditConstants.SUBMIT
//						&& record.getEngagementDTO().getStatusControlRisk() != InternalAuditConstants.REJECTED) {
//					activityObjectiveView.disable();
//					btnSaveActicityObjective.setVisible(false);
//					btnAddAcitivityObjective.setVisible(false);
//					btnSubmitActicityObjective.setVisible(false);
//				}
//			}
			
			activityObjectiveView.getDelete().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					boolean confirmed = Window.confirm("Are you sure you want to delete this Objective?");

					if (confirmed) {
						activityObjectiveView.removeFromParent();
						deleteActivityObjective();
					}
				}

			});

			usersActivityContainer.add(activityObjectiveView);

		}

		// Our's LIBRARY
		// Label lblLibHeading = new Label("----Library----");
		Button btnLibrary = new Button("Library");
		btnLibrary.setWidth("100px");
		// lblLibHeading.addStyleName("libraryText");
		if(record.getEngagementDTO().getStatusControlRisk() == InternalAuditConstants.REJECTED)
			hpnlButtons.setVisible(true); 
		else
			btnLibrary.setVisible(false);
		if (record.getEngagementDTO().getSelectedActivityObjectives().size() <= 0 || record.getEngagementDTO()
				.getSelectedActivityObjectives().get(0).getStatus() == InternalAuditConstants.SAVED) {
			btnLibrary.setVisible(true);
			boolean isAddInLibrary;
			for (ActivityObjective activityObjectiveLibrary : record.getEngagementDTO().getActivityObjectiveList()) {	
				 isAddInLibrary = true;
				if(record.getEngagementDTO().getSelectedActivityObjectives().size() > 0 || record.getEngagementDTO().getSelectedActivityObjectives() != null) {
					for(ActivityObjective selectedObjective : record.getEngagementDTO().getSelectedActivityObjectives()) {
						if(activityObjectiveLibrary.getObjectiveName().equals(selectedObjective.getObjectiveName())) {
							isAddInLibrary = false;
					}
				}
			}
				 if(isAddInLibrary)
					viewObjectivesLibraryList(activityObjectiveLibrary, vpnlActicityObjectiveContainer, usersActivityContainer, hpnlButtons);				
		}
	} 
		
		  if(userPackage.equalsIgnoreCase("Basic") || userPackage.equalsIgnoreCase("Gold")) {
			btnLibrary.setVisible(false); 
			btnAddAcitivityObjective.getElement().getStyle().setMarginLeft(1150, Unit.PX);
		  }
		  else
			  btnAddAcitivityObjective.getElement().getStyle().setMarginLeft(1050, Unit.PX);
		
	    btnAddAcitivityObjective.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final ActivityObjectiveViewNew act = new ActivityObjectiveViewNew();
				act.getlblReferenceNoData().setText(MyUtil.getRandom());
				act.getBtnSelectActivity().setVisible(false);
				hpnlButtons.setVisible(true);
				act.getData(act);// to add DeleteButton
				usersActivityContainer.add(act);
				act.getDelete().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						act.removeFromParent();
						if(usersActivityContainer.getWidgetCount()<1)
							hpnlButtons.setVisible(false);
						// usersActivityContainer.clear();
					}
				});

			}
		});

		btnSaveActicityObjective.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// con.clear();
				if(txtValidationObjectivesTab(usersActivityContainer))
					saveActivityObjective(usersActivityContainer, InternalAuditConstants.SAVED, con);
				else
					Window.alert("Enter some text in 'Activity Objective'");
			}
		});

		btnSubmitActicityObjective.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				boolean confirm = Window.confirm("Do you want to submit the \"Activity Objective\"\n" + 
						"Note: After submission you will not be able to make changes.");
				if(confirm) {
					if(txtValidationObjectivesTab(usersActivityContainer))
						saveActivityObjective(usersActivityContainer, InternalAuditConstants.SUBMIT, con);
					else
						Window.alert("Enter some text in 'Activity Objective'");
				}
			}
		});
		HorizontalPanel hpnlBtnHeader = new HorizontalPanel();
		hpnlBtnHeader.getElement().getStyle().setMarginTop(5, Unit.PX);
		hpnlBtnHeader.add(btnLibrary);
		hpnlBtnHeader.add(btnAddAcitivityObjective);
		hpnlBtnHeader.setHeight("40px");
		vpnlActicityObjective.add(hpnlBtnHeader);
		btnLibrary.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(vpnlActicityObjectiveContainer.getWidgetCount() < 1)
					new DisplayAlert("No Library added");
				else {
				final PopupsView p = new PopupsView(scrollActicityObjectiveContainer, "Library");
				p.getVpnlMain().setSize("500px", "300px");
//				Button btnClose = new Button("Close");
//				p.getVpnlMain().add(btnClose);
//				btnClose.getElement().getStyle().setMarginLeft(420, Unit.PX);
//				btnClose.addClickHandler(new ClickHandler() {
//
//					@Override
//					public void onClick(ClickEvent event) {
//						// TODO Auto-generated method stub
//						p.getVpnlMain().removeFromParent();
//						p.getPopup().removeFromParent();
//					}
//				});
			}
		}
	});
		vpnlActicityObjective.add(usersActivityContainer);
		// vpnlActicityObjective.add(vpnlActicityObjectiveContainer);

		// hpnlButtons.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		vpnlActicityObjective.add(hpnlButtons);
		VerticalLayoutContainer scrollMain = new VerticalLayoutContainer();
		scrollMain.setHeight("400px");
		scrollMain.setScrollMode(ScrollMode.AUTOY);
		scrollMain.add(vpnlActicityObjective);
		//When user directly add and submit Audit work in Audit Work Programme, this tab invisible
		if(record.getEngagementDTO().getSelectedAuditWorkforPrograms().size()>0 && record.getEngagementDTO().getSelectedActivityObjectives().size()<1) {
			if(record.getEngagementDTO().getSelectedAuditWorkforPrograms().get(0).getStatus() == InternalAuditConstants.SUBMIT 
					|| record.getEngagementDTO().getSelectedAuditWorkforPrograms().get(0).getStatus() == InternalAuditConstants.APPROVED )			
				scrollMain.setVisible(false);
			}
		cp.add(scrollMain);
		con.add(cp);

	}

	private boolean txtValidationObjectivesTab(VerticalPanel usersActivityContainer) {
		 boolean flag = false;
		for (int i = 0; i < usersActivityContainer.getWidgetCount(); i++) {
			ActivityObjectiveViewNew activityObjectiveView = (ActivityObjectiveViewNew) usersActivityContainer
					.getWidget(i);
			if(activityObjectiveView.getTxtAreaActivityObj().getText().length() > 0)
				flag = true;
			else
				flag = false;
		}
		return flag;
	}

	private void viewObjectivesLibraryList(final ActivityObjective activityObjectiveLibrary, final VerticalPanel vpnlActicityObjectiveContainer,
			final VerticalPanel usersActivityContainer, final HorizontalPanel hpnlButtons) {
		final ActivityObjectiveViewNew activityObjectiveViewLibrary = new ActivityObjectiveViewNew();
		activityObjectiveViewLibrary.setData(activityObjectiveLibrary);
		activityObjectiveViewLibrary.getTxtAreaActivityObj().setSize("400px", "120px");
		vpnlActicityObjectiveContainer.add(activityObjectiveViewLibrary);
		activityObjectiveViewLibrary.getBtnSelectActivity().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final ActivityObjectiveViewNew activityObjectiveSelected = new ActivityObjectiveViewNew();
				activityObjectiveViewLibrary.getData(activityObjectiveSelected);
				usersActivityContainer.add(activityObjectiveSelected);
				activityObjectiveSelected.getTxtAreaActivityObj().addStyleName("w3-sand");
				activityObjectiveViewLibrary.getTxtAreaActivityObj().addStyleName("w3-sand");
				activityObjectiveViewLibrary.getBtnSelectActivity().setVisible(false);
				hpnlButtons.setVisible(true);

				activityObjectiveSelected.getDelete().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						activityObjectiveSelected.removeFromParent();
						activityObjectiveViewLibrary.getBtnSelectActivity().setVisible(true);
						activityObjectiveSelected.getTxtAreaActivityObj().addStyleName("w3-sand");
						if(usersActivityContainer.getWidgetCount()<1)
							hpnlButtons.setVisible(false);
					}
				});

			}
		});
	}

	private void saveActivityObjective(final VerticalPanel usersActivityContainer, int status,
			AccordionLayoutContainer con) {
		ArrayList<ActivityObjective> activityObjectives = new ArrayList<ActivityObjective>();
		for (int i = 0; i < usersActivityContainer.getWidgetCount(); i++) {
			ActivityObjectiveViewNew activityObjectiveView = (ActivityObjectiveViewNew) usersActivityContainer
					.getWidget(i);
			ActivityObjective activityObjective = new ActivityObjective();
			activityObjectiveView.getTxtAreaActivityObj().setEnabled(false);
			activityObjectiveView.getDelete().setVisible(false);
			activityObjectiveView.getData(activityObjective);
			activityObjective.setSubProcessId(subProcess);
			// activityObjective.setSubProcessId(listSubProcess);
			// list added by moqeet
			// activityObjective.setChecked(true);
			activityObjectives.add(activityObjective);
		}
		saveActivityObjectives(activityObjectives, status, con);
	}

	private void saveActivityObjectives(ArrayList<ActivityObjective> activityObjectives, int status,
			final AccordionLayoutContainer con) {

		rpcService.saveActivityObjectives(activityObjectives, jobid, status, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed saving Acitivity Objectives:" + caught.getLocalizedMessage());

			}

			@Override
			public void onSuccess(String result) {
				// 2019 oct
				new DisplayAlert(result);
				refreshAccordionPanel(con);

			}

		});

	}

	private void saveRiskObjectives(ArrayList<RiskObjective> riskObjectives, int status,
			final AccordionLayoutContainer con) {
		rpcService.saveRiskObjectives(riskObjectives, jobid, status, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed saving Acitivity Objectives:" + caught.getLocalizedMessage());

			}

			@Override
			public void onSuccess(String result) {
				new DisplayAlert(result);
				refreshAccordionPanel(con);

			}
		});

	}

	private void saveExistingControls(ArrayList<SuggestedControls> suggestedControls) {
		rpcService.saveExistingControls(suggestedControls, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String result) {
				new DisplayAlert(result);
			}
		});

	}

	private void deleteActivityObjective() {
		rpcService.deleteActivityObjective(jobid, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed Deleting ActivityObjective");

			}

			@Override
			public void onSuccess(String result) {
				new DisplayAlert(result);

			}
		});

	}

	private void saveAuditWorkProgramme(ArrayList<AuditProgramme> auditWorkProgrammes,
			final AccordionLayoutContainer con) {
		rpcService.saveAuditWorkProgram(auditWorkProgrammes, selectedJobId, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail saving audit work");

			}

			@Override
			public void onSuccess(String result) {
				new DisplayAlert(result);
				refreshAccordionPanel(con);

			}
		});

	}

	public void refreshAccordionPanel(final AccordionLayoutContainer con) {
		rpcService.fetchAuditEngagement(jobid, new AsyncCallback<AuditEngagement>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed fetchingAuditEngagement in refreshAccordionPanel:" + caught.getLocalizedMessage());

			}

			@Override
			public void onSuccess(AuditEngagement result) {

				con.clear();
				showOptionsAccordian(result);

			}
		});
	}
	
	private void fetchCompanyPackage(int companyId) {
		rpcService.fetchCompanyPackage(companyId, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed fetchCompanyPackage:" + caught.getLocalizedMessage());
			}

			@Override
			public void onSuccess(String companyPackage) {
				// TODO Auto-generated method stub
				userPackage = companyPackage;   
			}
		});
	}

	private AsyncCallback<KickoffView> refreshMethod(final AccordionLayoutContainer con) {
		return new AsyncCallback<KickoffView>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(KickoffView result) {
				// panel.clear();
				refreshAccordionPanel(con);

			}
		};
	}
}
