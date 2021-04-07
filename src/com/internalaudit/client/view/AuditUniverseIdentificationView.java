package com.internalaudit.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.view.data.AuditUniverseStrategicViewData;
import com.internalaudit.shared.Employee;
import com.internalaudit.shared.Strategic;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;

import net.sourceforge.htmlunit.corejs.javascript.ast.Label;

public class AuditUniverseIdentificationView extends Composite {

	private static AuditUniverseIdentificationViewUiBinder uiBinder = GWT
			.create(AuditUniverseIdentificationViewUiBinder.class);

	interface AuditUniverseIdentificationViewUiBinder extends UiBinder<Widget, AuditUniverseIdentificationView> {
	}

	@UiField
	VerticalPanel mainPanel;
	private boolean headingAdded = false;
	private VerticalPanel vpnlStrategic;
	private VerticalPanel vpnlOperation;
	private VerticalPanel vpnlReporting;
	private VerticalPanel vpnlCompliance;
	private ArrayList<Anchor> listAllStrategicAnchors;
	private HorizontalPanel hpnlSelectAllButtons;
	private Anchor anchorApproveAll;
	private Anchor anchorSubmitAll;
	private Anchor anchorSaveAll;

	private ArrayList<AuditUniverseStrategicView> strategicList = new ArrayList<AuditUniverseStrategicView>();
	ContentPanel cp;
	private AuditUniverseStrategicView auditUniverseStrategicView;
	private Employee loggedInUser;

	public AuditUniverseIdentificationView(ContentPanel cp, Employee loggedInUser) {
		this.cp = cp;
		this.loggedInUser = loggedInUser;
		initWidget(uiBinder.createAndBindUi(this));
		bind();

	}

	private void bind() {

		auditUniverseIdentificationTabs();
		// btnAmend.setTitle("Send back for amendments");
	}

	public void setHandlers(final AuditUniverseStrategicViewData auditUniverseStrategicViewData, final VerticalPanel vpnlStrategicDataMain,
			final HorizontalPanel hpnlButtonsInitiator, final HorizontalPanel hpnlButtonsApprovar, final Image btnAdd, final int tab) {
		anchorApproveAll.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				boolean confirmed = Window.confirm("Are you sure to approve all jobs");
				if (confirmed) {
				auditUniverseStrategicViewData.approveAllStrategic(vpnlStrategicDataMain, hpnlButtonsInitiator, hpnlButtonsApprovar, btnAdd, tab);
				}
			}
		});
		
		anchorSubmitAll.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				boolean confirmed = Window.confirm("Are you sure to submit all jobs");
				if (confirmed) {
				auditUniverseStrategicViewData.submitAllStrategic(vpnlStrategicDataMain, hpnlButtonsInitiator, hpnlButtonsApprovar, btnAdd, tab);
				}
			}
		});
		
		anchorSaveAll.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				boolean confirmed = Window.confirm("Are you sure to save all jobs");
				if (confirmed) {
				auditUniverseStrategicViewData.saveAllStrategics(vpnlStrategicDataMain, hpnlButtonsInitiator, hpnlButtonsApprovar, btnAdd, tab);
				}
			}
		});
	}

	private Widget flexPanelLayoutStrategic(final int tab) {
		ScrollPanel strategicPanel = new ScrollPanel();
		final VerticalPanel vpnlMain = new VerticalPanel();
		final VerticalPanel vpnlStrategic = new VerticalPanel();
		strategicPanel.add(vpnlStrategic);
		strategicPanel.setSize("1200px", "300px");
		
		hpnlSelectAllButtons = new HorizontalPanel();
		listAllStrategicAnchors = new ArrayList<Anchor>();
		anchorsAllStrategicSelectionView(hpnlSelectAllButtons);
		hpnlSelectAllButtons.addStyleName("w3-right");
		if(loggedInUser.getDesignation().equalsIgnoreCase("Head of Internal Audit")) {
			vpnlMain.add(hpnlSelectAllButtons);
		}
		// final HorizontalPanel hpnlStrategic = new HorizontalPanel();
		// hpnlStrategic.setWidth("700px");
		HorizontalPanel hpnlStrategicHeader = new HorizontalPanel();
		hpnlStrategicHeader.add(new AuditUniverseStrategicViewHeading());

		final Image btnAdd = new Image("images/add.png");
		btnAdd.addStyleName("pointerStyle");
		hpnlStrategicHeader.add(btnAdd);

		vpnlMain.add(hpnlStrategicHeader);
		vpnlMain.add(strategicPanel);

		final HorizontalPanel hpnlButtonsInitiator = new HorizontalPanel();
		final HorizontalPanel hpnlButtonsApprovar = new HorizontalPanel();
		HorizontalPanel hpnlSpace = new HorizontalPanel();
		hpnlSpace.setWidth("750px");
		hpnlButtonsInitiator.add(hpnlSpace);
		hpnlButtonsInitiator.setSpacing(2);
		hpnlButtonsInitiator.setVisible(false);
		hpnlButtonsApprovar.add(hpnlSpace);
		hpnlButtonsApprovar.setSpacing(2);
		hpnlButtonsApprovar.setVisible(false);
		VerticalPanel vpnlStrategicDataMain = new VerticalPanel();
		// btnAdd.getElement().getStyle().setPaddingLeft(1154, Unit.PX);
		btnAdd.getElement().getStyle().setMarginTop(10, Unit.PX);
		// vpnlStrategic.add(btnAdd);

		vpnlStrategic.add(vpnlStrategicDataMain);
		vpnlStrategic.add(hpnlButtonsInitiator);
		vpnlStrategic.add(hpnlButtonsApprovar);
		// vpnlStrategicData.add(hpnlStrategic);
		auditUniverseStrategicView = new AuditUniverseStrategicView();
		auditUniverseStrategicView.getAuditUniverseStrategicViewData().fetchDivisions();
		auditUniverseStrategicView.getAuditUniverseStrategicViewData().fetchObjectiveOwners();

		vpnlStrategicDataMain.clear();
//		VerticalPanel vlcAddNew = new VerticalPanel();
//		vpnlStrategicDataMain.add(vlcAddNew);
		strategicList.clear();
//		VerticalPanel vpnlStrategicData = new VerticalPanel();
//		vpnlStrategicDataMain.add(vpnlStrategicData);
//		ArrayList<Strategic> listStrategicToSaveAll = new ArrayList<Strategic>();
		auditUniverseStrategicView.getAuditUniverseStrategicViewData().setListAnchors(listAllStrategicAnchors);
		addNewStrategic(listAllStrategicAnchors, tab, btnAdd, hpnlButtonsInitiator, hpnlButtonsApprovar, vpnlStrategicDataMain);
		auditUniverseStrategicView.getAuditUniverseStrategicViewData().fetchStrategic(vpnlStrategicDataMain,
				hpnlButtonsInitiator, hpnlButtonsApprovar, btnAdd, tab);
		setHandlers(auditUniverseStrategicView.getAuditUniverseStrategicViewData(), vpnlStrategicDataMain, hpnlButtonsInitiator, hpnlButtonsApprovar, btnAdd, tab);
			return vpnlMain;
	}

	private void anchorsAllStrategicSelectionView(HorizontalPanel hpnlSelectAllButtons) {
		hpnlSelectAllButtons.clear();
		listAllStrategicAnchors.clear();
		
		anchorApproveAll = new Anchor("Approve all");
		anchorSubmitAll = new Anchor("Submit all");
		anchorSaveAll = new Anchor("Save all");

		hpnlSelectAllButtons.add(anchorSaveAll);
		hpnlSelectAllButtons.add(anchorSubmitAll);
		hpnlSelectAllButtons.add(anchorApproveAll);
		
		hpnlSelectAllButtons.setCellWidth(anchorSaveAll, "65x");
		hpnlSelectAllButtons.setCellWidth(anchorSubmitAll, "70px");
		hpnlSelectAllButtons.setCellWidth(anchorApproveAll, "70px");
		
		listAllStrategicAnchors.add(anchorSaveAll);
		listAllStrategicAnchors.add(anchorSubmitAll);
		listAllStrategicAnchors.add(anchorApproveAll);
	}

	private void addNewStrategic(final ArrayList<Anchor> listAllStrategicAnchors, final int tab, final Image btnAdd, final HorizontalPanel hpnlButtonsInitiator,
			final HorizontalPanel hpnlButtonsApprovar, final VerticalPanel vpnlStrategicDataMain) {
		btnAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
//				 btnAdd.setVisible(false);
				auditUniverseStrategicView = new AuditUniverseStrategicView();
				auditUniverseStrategicView.getHpnlButtonsApprovar().setVisible(false);
				auditUniverseStrategicView.getHpnlButtonInitiator().setVisible(true);
				auditUniverseStrategicView.getAuditUniverseStrategicViewData()
						.fetchDepartmentsForNewRecord(auditUniverseStrategicView);
				// auditUniverseStrategicView.getAuditUniverseStrategicViewData().fetchObjectiveOwnersForNewRecord(auditUniverseStrategicView);
//				vpnlStrategicData.clear();
				auditUniverseStrategicView.getAuditUniverseStrategicViewData().setListAnchors(listAllStrategicAnchors);
				vpnlStrategicDataMain.add(auditUniverseStrategicView);
				// vpnlStrategicData.insert(auditUniverseStrategicView, 1);
				// Plus icon was not working in Reoprting and Compliance
				auditUniverseStrategicView.getBtnSave().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						auditUniverseStrategicView.getAuditUniverseStrategicViewData().saveStrategic(
								auditUniverseStrategicView, vpnlStrategicDataMain, hpnlButtonsInitiator,
								hpnlButtonsApprovar, btnAdd, "save", tab, auditUniverseStrategicView.getBtnSave());
						anchorsAllStrategicSelectionView(hpnlSelectAllButtons);	
						setHandlers(auditUniverseStrategicView.getAuditUniverseStrategicViewData(), vpnlStrategicDataMain, hpnlButtonsInitiator, hpnlButtonsApprovar, btnAdd, tab);
//						auditUniverseStrategicView.clearStrategicView();
//						 btnAdd.setVisible(true);
					}
				});

				auditUniverseStrategicView.getBtnSubmit().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						auditUniverseStrategicView.getAuditUniverseStrategicViewData().saveStrategic(
								auditUniverseStrategicView, vpnlStrategicDataMain, hpnlButtonsInitiator,
								hpnlButtonsApprovar, btnAdd, "submit", tab, auditUniverseStrategicView.getBtnSubmit());
						anchorsAllStrategicSelectionView(hpnlSelectAllButtons);	
						setHandlers(auditUniverseStrategicView.getAuditUniverseStrategicViewData(), vpnlStrategicDataMain, hpnlButtonsInitiator, hpnlButtonsApprovar, btnAdd, tab);
//						auditUniverseStrategicView.clearStrategicView();
//						 btnAdd.setVisible(true);
					}
				});
			}
		});
	}

	public void auditUniverseIdentificationTabs() {

		VerticalPanel vp = new VerticalPanel();

		final PlainTabPanel panel = new PlainTabPanel();
		vpnlStrategic = new VerticalPanel();
		vpnlOperation = new VerticalPanel();
		vpnlReporting = new VerticalPanel();
		vpnlCompliance = new VerticalPanel();
		panel.setResizeTabs(true);

		panel.add(vpnlStrategic, "Strategic");
		panel.add(vpnlOperation, "Operations");
		panel.add(vpnlReporting, "Reporting");
		panel.add(vpnlCompliance, "Compliance");

		vpnlStrategic.add(flexPanelLayoutStrategic(0));

		panel.addSelectionHandler(new SelectionHandler<Widget>() {

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				TabPanel panel = (TabPanel) event.getSource();
				Widget w = event.getSelectedItem();
				TabItemConfig config = panel.getConfig(w);
				if (config.getText().equalsIgnoreCase("strategic")) {
					vpnlStrategic.clear();
					vpnlStrategic.add(flexPanelLayoutStrategic(0));
				} else if (config.getText().equalsIgnoreCase("operations")) {
					vpnlOperation.clear();
					vpnlOperation.add(flexPanelLayoutStrategic(1));
				} else if (config.getText().equalsIgnoreCase("Reporting")) {
					vpnlReporting.clear();
					vpnlReporting.add(flexPanelLayoutStrategic(2));
				} else if (config.getText().equalsIgnoreCase("Compliance")) {
					vpnlCompliance.clear();
					vpnlCompliance.add(flexPanelLayoutStrategic(3));
				}

			}
		});
		vp.add(panel);
		mainPanel.add(vp);
	}

}
