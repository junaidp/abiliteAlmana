package com.internalaudit.client.view;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.presenter.MainPresenter.Display;
import com.internalaudit.client.widgets.TableauAbilite;
import com.internalaudit.client.widgets.TableauExcel;
import com.internalaudit.client.widgets.TableauReports;
import com.internalaudit.shared.Employee;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.DialogMessages;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.box.ProgressMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;

public class MainView extends Composite implements Display {

	private InternalAuditServiceAsync rpcService;
	private Employee loggedInUser;
	private Anchor logOut = new Anchor("Logout");
	private Anchor feedBack = new Anchor("Help/Feedback");
	private Anchor changePassword = new Anchor("Change Password");	
	private Anchor createCompany = new Anchor("Add Company");
	private Anchor createUser = new Anchor("Add User");
//	private Anchor anchorSettings = new Anchor("Settings");
	private ListBox listYears = new ListBox();
	private Anchor welcome = new Anchor("");
	private VerticalPanel vpnlAuditScheduing = new VerticalPanel();
	private VerticalPanel vpnlAuditEngagement = new VerticalPanel();
	private VerticalPanel vpnlDashBoard = new VerticalPanel();
	private VerticalPanel reportingView = new VerticalPanel();
	private VerticalPanel followUpView = new VerticalPanel();
	private VerticalPanel reportsView = new VerticalPanel();
	private HorizontalPanel footer = new HorizontalPanel();
	private VerticalLayoutContainer vpnlDashBoardNew = new VerticalLayoutContainer();
	private VerticalPanel panelBar = new VerticalPanel();
	private VerticalPanel containerAuditPlanning = new VerticalPanel();
	Logger logger = Logger.getLogger(MainView.class);

	PlainTabPanel panel = new PlainTabPanel();
	HorizontalPanel checkpanel = new HorizontalPanel();
	VerticalPanel panelImages = new VerticalPanel();
	VerticalPanel panelSideBar = new VerticalPanel();

	public MainView(Employee loggedInUser, HandlerManager eventBus, InternalAuditServiceAsync rpcService) {
		// new code
		logger.setLevel(Level.DEBUG);
		logger.info("Signed In on from logmain view" + new Date());
		panel.getElement().getStyle().setMarginLeft(2, Unit.PX);
		panelImages.setWidth("110px");
		panelImages.setHeight("200px");
		this.rpcService = rpcService;
		// panelImages.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		// panelImages.getElement().getStyle().setBorderWidth(2, Unit.PX);
		// Button btn = new Button("Hello its visible");
		// // panelImages.add(btn);
		// Label dash = new Label("DASHBOARD");
		// panelImages.add(dash);
		// Image dashboard = new Image("dashboard.png");
		// dashboard.setWidth("80px");
		// dashboard.setHeight("40px");
		// panelImages.add(dashboard);
		// Label rep = new Label("REPORTS");
		// panelImages.add(rep);?
		// Image reports = new Image("Reports.png");
		// reports.setHeight("40px");
		// reports.setWidth("80px");
		// panelImages.add(reports);
		// Label lblwrk = new Label("WORK ITEM");
		// panelImages.add(lblwrk);
		// Image workItem = new Image("work items.jpg");
		// workItem.setHeight("40px");
		// workItem.setWidth("80px");
		// panelImages.add(workItem);
		// VerticalPanel panelTeam = new VerticalPanel();
		// panelTeam.addStyleName("w3-margin");
		// VerticalPanel panelClient = new VerticalPanel();
		// panelClient.addStyleName("w3-margin");
		// 2018
		SideBarView sideBarView = new SideBarView(loggedInUser, eventBus);
		panelSideBar.add(sideBarView);
		sideBarView.getImgUpgrade().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				confirmUpgradeSoftware();
			}
		});

		FocusPanel vpTeamMgm = putImageInCard("TEAM MANAGEMENT", "team management.jpg");
		FocusPanel vpClientMgm = putImageInCard("CLIENT MANAGEMENT", "client mang.png");

		panelImages.add(vpTeamMgm);
		panelImages.add(vpClientMgm);

		// vpTeamMgm.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		//
		//
		// PopupsView pp = new PopupsView(todoview, "");
		// pp.getLabelheading().setText("To Do");
		// pp.getVpnlMain().setWidth("320px");
		// pp.getHpnlSPace().setWidth("320px");
		// pp.getVpnlMain().setHeight("320px");
		// }
		// });
		//
		// vpClientMgm.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		//
		// PopupsView pp = new PopupsView(informationreq, "");
		// pp.getLabelheading().setText("Information Request");
		// pp.getVpnlMain().setTitle("Information Request");
		// pp.getVpnlMain().setWidth("400px");
		// pp.getHpnlSPace().setWidth("400px");
		// pp.getVpnlMain().setHeight("530px");
		//
		// }
		// });

		// endshere
		this.loggedInUser = loggedInUser;
		// auditPlanningView = new AuditPlanningView(loggedInUser);Commented
		// when we added dashboard inside a tab

		HorizontalPanel hpnlMain = new HorizontalPanel();
		Image imgHeader = new Image("images/trans.png");
		// imgHeader.getElement().getStyle().setPaddingLeft(50, Unit.PX);
		// imgHeader.addStyleName("w3-margin");
		imgHeader.getElement().getStyle().setMarginBottom(10, Unit.PX);
		VerticalPanel vp = new VerticalPanel();
		VerticalPanel vpnl = new VerticalPanel();
		HorizontalPanel hpnlSpace = new HorizontalPanel();
		VerticalPanel hpnlHeader = new VerticalPanel();
		vp.add(hpnlMain);
		// hpnl.addStyleName("w3-bar-block w3-border w3-light-blue");
		hpnlMain.add(imgHeader);
		hpnlMain.add(hpnlHeader);
		// hpnlHeader.addStyleName("blueBackground");
		// hpnlHeader.setWidth(Window.getClientWidth()-imgHeader.getWidth()-170+"px");
		hpnlHeader.setWidth("1050px");
		hpnlHeader.setHeight("91px");
		hpnlHeader.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		vpnl.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		// SelectionHandler<Widget> handler = new SelectionHandler<Widget>() {
		// @Override
		// public void onSelection(SelectionEvent<Widget> event) {
		// TabPanel panel = (TabPanel) event.getSource();
		// Widget w = event.getSelectedItem();
		// TabItemConfig config = panel.getConfig(w);
		// Info.display("Message", "'" + config.getText() + "' Selected");
		// }
		// };

		// logOut.addStyleName("logout");

		// panel.setWidth(Window.getClientWidth()-100+"px");
		// panel.setWidth("880px");
		panel.setWidth("1200px");
		panel.setResizeTabs(true);

		if (loggedInUser.getFromInternalAuditDept().equalsIgnoreCase("yes")) {
			panel.add(vpnlDashBoard, "DashBoard");
			panel.add(containerAuditPlanning, "Audit Planning");
			panel.add(vpnlAuditScheduing, "Audit Scheduling");
			panel.add(vpnlAuditEngagement, "Audit Engagement");
			panel.add(reportingView, "Reporting");
			panel.add(followUpView, "Follow Up");

			TabItemConfig config = new TabItemConfig("");
			config.setEnabled(false);

			// panel.add(new EmployeeDashBoardView(), "WorkItems");
			// removed by Moqeet as it's not required yet
			panel.add(reportsView, "Reports");

			// 2018 new
			// vpnlDashBoardNew.add( new DashboardNew());
			panel.add(vpnlDashBoardNew, "Analytics");
			// panel.add((IsWidget) new Dashboard(),"NewDashboard");

		} else {
			panel.add(reportingView, "Reporting");
			panel.add(followUpView, "Follow Up");
			panel.setActiveWidget(reportingView);

		}
		// 2018 if(loggedInUser.getEmployeeId().getUserId().getUserId() == 1){
		// addTableauTabs();
		// }
		if (loggedInUser.getEmployeeId() == 1 && loggedInUser.getEmployeeName().contains("Faheem")) {
			addTableauTabs();
		}

		TabItemConfig config = new TabItemConfig("Reporting");

		// panel.insert(reportingView, 3, config);
		VerticalPanel vpnlTabPanel = new VerticalPanel();
		// vpnlTabPanel.addStyleName("centerPanel");

		vp.setWidth("100%");
		vp.add(vpnlTabPanel);
		// checkpanel.add(panel);
		// checkpanel.add(panelImages);
		// vpnlTabPanel.add(panel);
		checkpanel.add(panelSideBar);
		// checkpanel.add(panelImages);
		checkpanel.add(panel);

		panelBar.addStyleName("w3-bar-block w3-border w3-light-blue");
		// vpnlTabPanel.getElement().getStyle().setPaddingLeft(12, Unit.PX);

		vpnlTabPanel.add(checkpanel);
		// vpnlTabPanel.add(panelImages);
		// selectYear().addStyleName("w3-bar-item w3-right");

		VerticalPanel vpnlLogo = new VerticalPanel();
		Image imgLogo = new Image("images/AGH-logo.jpg");
//		Image imgLogo = new Image("images/logo/hyphenlogo.png");
		imgLogo.setSize("240px", "60px");	
		vpnlLogo.add(imgLogo);
		
		vpnl.add(vpnlLogo);
		vpnl.add(selectYear());
		vpnl.add(welcome);
		// hpnl.add(panelBar);
		// panelBar.add(welcome);
		panelBar.add(feedBack);
//		panelBar.add(anchorSettings); 
		panelBar.add(changePassword);
		panelBar.add(logOut);
		// hpnl.add(welcome); // Welcome <name>
		// welcome.addStyleName("white");
		// welcome.addStyleName(" w3-bar-item w3-hover-blue w3-right");
		welcome.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PopupPanel p = new PopupPanel();
				p.add(panelBar);
				p.showRelativeTo(welcome);
				p.isAutoHideEnabled();
				p.setAutoHideEnabled(true);

			}
		});
		welcome.setWordWrap(false);
		hpnlHeader.add(hpnlSpace);
		hpnlSpace.setWidth("65%");
		hpnlHeader.add(vpnl);
		//2020 hamza cmnt
		if (loggedInUser.getEmployeeName().equalsIgnoreCase("Muhammad Faheem Piracha")
				&& loggedInUser.getEmployeeId() == 1) {
			vpnl.add(createCompany);
			vpnl.add(createUser);
			
		}
		logOut.addStyleName("white");
		feedBack.addStyleName("white");
		feedBack.addStyleName("  w3-bar-item w3-hover-blue w3-right");
		logOut.addStyleName(" w3-bar-item w3-hover-blue w3-right");
		changePassword.addStyleName("white w3-bar-item w3-hover-blue w3-right");
//		anchorSettings.addStyleName("white w3-bar-item w3-hover-blue w3-right");
		vpnl.setSpacing(2);
		vpnl.setWidth("0%");
		vpnl.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		initWidget(vp);
	}
		
	private void confirmUpgradeSoftware() {
		boolean confirm = Window.confirm("This will install the upgraded version of abilite, you want to continue ?");
		if(confirm)
			upgradeSoftware();
	}
	
	private void upgradeSoftware() {
		progressUpgradeSoftware();
		final ProgressMessageBox p = new ProgressMessageBox("Updating project", "Please Wait ..");
		p.getButton(PredefinedButton.OK).setVisible(false);
		p.show();
		rpcService.upgradeSoftware(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable arg0) {
				Window.alert("Unable to upgrade software");
			}

			@Override
			public void onSuccess(String arg0) {
				final DelayedTask d = new DelayedTask() {
				    @Override
				    public void onExecute() {
				        p.hide();
				    	History.newItem("login");
				    }
				};
				d.delay(20000);
			}
		}); 
	}
	
	private void progressUpgradeSoftware() { 
		
	}
	
	private FocusPanel putImageInCard(String lblName, String imgSource) {
		Label lbl = new Label(lblName);
		lbl.setWidth("125px");
		lbl.getElement().getStyle().setColor("White");
		Image img = new Image(imgSource);
		lbl.setWordWrap(false);
		// lbl.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		img.getElement().getStyle().setMarginLeft(30, Unit.PX);
		img.addStyleName("point");
		lbl.addStyleName("point");
		img.setHeight("60px");
		img.setWidth("80px");
		VerticalPanel vpTeamMgm = new VerticalPanel();
		vpTeamMgm.addStyleName("w3-card-4");
		vpTeamMgm.add(img);
		VerticalPanel vpTeamMgmLbl = new VerticalPanel();
		vpTeamMgmLbl.add(lbl);
		vpTeamMgmLbl.addStyleName("w3-container w3-center w3-blue");
		vpTeamMgm.add(vpTeamMgmLbl);
		FocusPanel focusPanel = new FocusPanel();
		focusPanel.add(vpTeamMgm);
		return focusPanel;
	}

	private void addTableauTabs() {
		final VerticalPanel vpTab = new VerticalPanel();
		final VerticalPanel vpTabAb = new VerticalPanel();
		final VerticalPanel vpTabEx = new VerticalPanel();

		vpTabAb.setWidth("100%");
		vpTab.setWidth("100%");
		vpTabEx.setWidth("100%");

		panel.add(vpTab, "Tableau");
		panel.add(vpTabAb, "Tableau Abilite");
		panel.add(vpTabEx, "Tableau Excel");

		panel.addSelectionHandler(new SelectionHandler<Widget>() {

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				if (event.getSelectedItem().equals(vpTab)) {
					vpTab.add(new TableauReports());
				} else if (event.getSelectedItem().equals(vpTabAb)) {
					vpTabAb.add(new TableauAbilite());
				} else if (event.getSelectedItem().equals(vpTabEx)) {
					vpTabEx.add(new TableauExcel());
				}
			}
		});
	}

	public Widget selectYear() {
		HorizontalPanel hpnlYear = new HorizontalPanel();
		VerticalPanel vpnlYear = new VerticalPanel();
		Label lblSelectYear = new Label("Year");
		// vpnlYear.add(lblSelectYear);
		listYears.addStyleName("yearList");
		vpnlYear.add(listYears);
		hpnlYear.add(vpnlYear);
		hpnlYear.getElement().getStyle().setPaddingTop(10, Unit.PX);
		hpnlYear.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		vpnlYear.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		// listYears.addItem("2014");

		// listYears.addItem("2015");
		// listYears.addItem("2016");
		// listYears.addItem("2017");
		// listYears.addItem("2018");
		// listYears.addItem("2019");
		// listYears.addItem("2020");
		// if(selectedYear!=0){
		// for(int i=0; i< listYears.getItemCount(); i++){
		// if(Integer.parseInt(listYears.getValue(i)) == selectedYear){
		// listYears.setSelectedIndex(i);
		// }
		// }
		// }
		return hpnlYear;

	}

	public Employee getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Employee loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public Anchor getLogOut() {
		return logOut;
	}

	public void setLogOut(Anchor logOut) {
		this.logOut = logOut;
	}

	public Anchor getWelcome() {
		return welcome;
	}

	public void setWelcome(Anchor welcome) {
		this.welcome = welcome;
	}

	public VerticalPanel getVpnlAuditScheduing() {
		return vpnlAuditScheduing;
	}

	public void setVpnlAuditScheduing(VerticalPanel vpnlAuditScheduing) {
		this.vpnlAuditScheduing = vpnlAuditScheduing;
	}

	public PlainTabPanel getPanel() {
		return panel;
	}

	public void setPanel(PlainTabPanel panel) {
		this.panel = panel;
	}

	public VerticalPanel getVpnlAuditEngagement() {
		return vpnlAuditEngagement;
	}

	public void setVpnlAuditEngagement(VerticalPanel vpnlAuditEngagement) {
		this.vpnlAuditEngagement = vpnlAuditEngagement;
	}

	public VerticalPanel getReportingView() {
		return reportingView;
	}

	public void setReportingView(VerticalPanel reportingView) {
		this.reportingView = reportingView;
	}

	public ListBox getListYears() {
		return listYears;
	}

	public void setListYears(ListBox listYears) {
		this.listYears = listYears;
	}

	public VerticalPanel getVpnlDashBoard() {
		return vpnlDashBoard;
	}

	public void setVpnlDashBoard(VerticalPanel vpnlDashBoard) {
		this.vpnlDashBoard = vpnlDashBoard;
	}

	public VerticalPanel getReportsView() {
		return reportsView;
	}

	public void setReportsView(VerticalPanel reportsView) {
		this.reportsView = reportsView;
	}

	public Anchor getCreateCompany() {
		return createCompany;
	}

	public void setCreateCompany(Anchor createCompany) {
		this.createCompany = createCompany;
	}

	public Anchor getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Anchor createUser) {
		this.createUser = createUser;
	}

	public Anchor getFeedBack() {
		return feedBack;
	}

	public VerticalLayoutContainer getVpnlDashBoardNew() {
		return vpnlDashBoardNew;
	}

	public VerticalPanel getContainerAuditPlanning() {
		return containerAuditPlanning;
	}

	public VerticalPanel getFollowUpView() {
		return followUpView;
	}

	public Anchor getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(Anchor changePassword) {
		this.changePassword = changePassword;
	}

//	public Anchor getAnchorSettings() {
//		return anchorSettings;
//	}
//
//	public void setAnchorSettings(Anchor anchorSettings) {
//		this.anchorSettings = anchorSettings;
//	}
}
