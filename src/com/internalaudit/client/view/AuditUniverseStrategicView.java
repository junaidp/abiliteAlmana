package com.internalaudit.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.view.data.AuditUniverseStrategicViewData;
import com.sencha.gxt.widget.core.client.button.IconButton;
import com.sencha.gxt.widget.core.client.form.DateField;

public class AuditUniverseStrategicView extends Composite {

	private static AuditUniverseStrategicViewUiBinder uiBinder = GWT.create(AuditUniverseStrategicViewUiBinder.class);

	interface AuditUniverseStrategicViewUiBinder extends UiBinder<Widget, AuditUniverseStrategicView> {
	}

	@UiField
	VerticalPanel mainPanel;
	IconButton btnAdd = new IconButton("+..");
	private AuditUniverseStrategicViewData auditUniverseStrategicViewData = new AuditUniverseStrategicViewData();
	private ListBox lstObjectiveOwner = new ListBox();
	private ListBox listRelevantDepartment = new ListBox();
	private DateField objectiveAchievementDate = new DateField();
	private TextArea strategicObjective = new TextArea();
	private Label feedback = new Label(" Feedback ");
	private Image submitted = new Image(" images/tick.png ");

	private int strategicId;
	private ButtonRound btnSave = new ButtonRound("Save");
	private ButtonRound btnSubmit = new ButtonRound("Submit");
	private ButtonRound btnApprove = new ButtonRound("Approve");
	private ButtonRound btnDecline = new ButtonRound("Delete");
	private ButtonRound btnDeclineInitiator = new ButtonRound("Delete");
	private ButtonRound btnFeedback = new ButtonRound("Feedback");
	private HorizontalPanel hpnlButtonInitiator = new HorizontalPanel();
	private HorizontalPanel hpnlButtonsApprovar = new HorizontalPanel();
	private String comment;
	private Label lblStrategicId = new Label();
	private ScrollPanel relevantDepartmentPanel = new ScrollPanel();
	private VerticalPanel vpnlRelevantDepartmentPanel = new VerticalPanel();
	private ListBox listBoxDivision = new ListBox();

	public AuditUniverseStrategicView() {
		initWidget(uiBinder.createAndBindUi(this));
		relevantDepartmentPanel.setVisible(false);
		bind();
		submitted.setVisible(false);
		feedback.setVisible(false);

	}

	private void bind() {
		mainPanelLayout();
	}

	private void mainPanelLayout() {
	strategicObjective.getElement().setPropertyString("placeholder","Enter Objective");
	listBoxDivision.setWidth("230px");
	listRelevantDepartment.setWidth("240px");
	strategicObjective.setSize("600px", "50px");
	VerticalPanel vpnlStrategicId = new VerticalPanel();
	VerticalPanel vpnlStrategicObjective = new VerticalPanel();
	//vpnlStrategicObjective.setWidth("805px");
	VerticalPanel vpnlLstObjectiveOwner = new VerticalPanel();
	VerticalPanel vpnlRelevantDivision = new VerticalPanel();
	VerticalPanel vpnlRelevantDepartments = new VerticalPanel();
	VerticalPanel vpnlObjectiveAchievementDate = new VerticalPanel();
	Label lblStrategicObjective = new Label(AuditConstants.STRATEGICOBJECTIVE);
	Label lblOwner = new Label(AuditConstants.OBJECTIVEOWNER);
	Label lblDepartment = new Label(AuditConstants.OBJECTIVEDEPT);
	Label lblDate = new Label(AuditConstants.OBJECTIVEDATE);

	lblStrategicObjective.setWidth("600px");
	lblStrategicObjective.addStyleName("navybluebold");
	lblOwner.addStyleName("blue");
	lblDepartment.addStyleName("blue");
	lblDate.addStyleName("blue");
	submitted.addStyleName("pointer");
	submitted.setTitle("submitted");

	vpnlStrategicObjective.add(strategicObjective);
	vpnlLstObjectiveOwner.add(lstObjectiveOwner);
	vpnlStrategicId.add(lblStrategicId);
	listRelevantDepartment.setMultipleSelect(true);
	vpnlRelevantDivision.add(listBoxDivision );
	ScrollPanel scrollDivisionPanel =  new ScrollPanel();
	vpnlRelevantDivision.add(scrollDivisionPanel);
	//scrollDivisionPanel.add(vpnlRelevantDivisionPanel);
	scrollDivisionPanel.setHeight("30px");
	vpnlRelevantDivision.setSpacing(1);
	vpnlRelevantDepartments.setSpacing(1);
	vpnlRelevantDepartments.add(listRelevantDepartment);
	vpnlRelevantDepartments.add(relevantDepartmentPanel);
	relevantDepartmentPanel.setHeight("30px");
	vpnlObjectiveAchievementDate.add(objectiveAchievementDate);

	HorizontalPanel hpnlStrategic = new HorizontalPanel();
	// hpnlStrategic.add(strategicObjective);
	// hpnlStrategic.add(lstObjectiveOwner);
	// hpnlStrategic.add(relevantDepartment);
	// hpnlStrategic.add(objectiveAchievementDate);
	lblStrategicId.addStyleName("blue");
	lblStrategicId.setWidth("45px");
	hpnlStrategic.add(vpnlStrategicId);
	hpnlStrategic.add(vpnlStrategicObjective);
	HorizontalPanel hpnlComments = new HorizontalPanel();

	hpnlComments.setWidth("25px");
	hpnlComments.add(feedback);
	feedback.getElement().getStyle().setMarginLeft(25, Unit.PX);
	hpnlComments.add(submitted);
	hpnlStrategic.add(hpnlComments);
	hpnlStrategic.add(vpnlRelevantDivision);
	hpnlStrategic.add(vpnlRelevantDepartments);

	hpnlButtonInitiator.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	mainPanel.add(hpnlStrategic);
	mainPanel.add(hpnlButtonInitiator);
	mainPanel.add(hpnlButtonsApprovar);
	HorizontalPanel hpnlSpace = new HorizontalPanel();
	HorizontalPanel hpnlSpaceApprovar = new HorizontalPanel();

	hpnlButtonsApprovar.add(hpnlSpaceApprovar);
	hpnlButtonsApprovar.add(btnDecline);
	hpnlButtonsApprovar.add(btnFeedback);
	hpnlButtonsApprovar.add(btnApprove);
	hpnlButtonsApprovar.setSpacing(2);

	hpnlButtonsApprovar.setVisible(false);
	hpnlButtonInitiator.setVisible(false);

	hpnlSpace.setWidth("870px");
	hpnlSpaceApprovar.setWidth("870px");
	hpnlButtonInitiator.add(hpnlSpace);
	hpnlButtonInitiator.add(btnDeclineInitiator);
	btnDeclineInitiator.setVisible(false);
	hpnlButtonInitiator.add(btnSave);
	hpnlButtonInitiator.add(btnSubmit);
	btnSave.setWidth("70px");
	btnSubmit.setWidth("70px");
	btnDeclineInitiator.setWidth("70px");
	hpnlButtonInitiator.setSpacing(2);
	hpnlStrategic.setWidth("900px");
	mainPanel.addStyleName("form-row");
	}
	
	public void clearStrategicView() {
		strategicObjective.setText("");
		strategicObjective.setText("");
		listBoxDivision.clear();
		listRelevantDepartment.clear();
	}

	public IconButton getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(IconButton btnAdd) {
		this.btnAdd = btnAdd;
	}

	public AuditUniverseStrategicViewData getAuditUniverseStrategicViewData() {
		return auditUniverseStrategicViewData;
	}

	public void setAuditUniverseStrategicViewData(AuditUniverseStrategicViewData auditUniverseStrategicViewData) {
		this.auditUniverseStrategicViewData = auditUniverseStrategicViewData;
	}

	public ListBox getLstObjectiveOwner() {
		return lstObjectiveOwner;
	}

	public void setLstObjectiveOwner(ListBox lstObjectiveOwner) {
		this.lstObjectiveOwner = lstObjectiveOwner;
	}

	public ListBox getListRelevantDepartment() {
		return listRelevantDepartment;
	}

	public void setListRelevantDepartment(ListBox listRelevantDepartment) {
		this.listRelevantDepartment = listRelevantDepartment;
	}
	
	public ListBox getListBoxDivision() {
		return listBoxDivision;
	}

	public void setListBoxDivision(ListBox listBoxDivision) {
		this.listBoxDivision = listBoxDivision;
	}

	public DateField getObjectiveAchievementDate() {
		return objectiveAchievementDate;
	}

	public void setObjectiveAchievementDate(DateField objectiveAchievementDate) {
		this.objectiveAchievementDate = objectiveAchievementDate;
	}

	public TextArea getStrategicObjective() {
		return strategicObjective;
	}

	public void setStrategicObjective(TextArea strategicObjective) {
		this.strategicObjective = strategicObjective;
	}

	public int getStrategicId() {
		return strategicId;
	}

	public void setStrategicId(int strategicId) {
		this.strategicId = strategicId;
	}

	public Label getFeedback() {
		return feedback;
	}

	public void setFeedback(Label feedback ) {
		this.feedback = feedback ;
	}

	public ButtonRound getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(ButtonRound btnSave) {
		this.btnSave = btnSave;
	}

	public ButtonRound getBtnSubmit() {
		return btnSubmit;
	}

	public void setBtnSubmit(ButtonRound btnSubmit) {
		this.btnSubmit = btnSubmit;
	}

	public HorizontalPanel getHpnlButtonInitiator() {
		return hpnlButtonInitiator;
	}

	public void setHpnlButtonInitiator(HorizontalPanel hpnlButtonInitiator) {
		this.hpnlButtonInitiator = hpnlButtonInitiator;
	}

	public ButtonRound getBtnApprove() {
		return btnApprove;
	}

	public void setBtnApprove(ButtonRound btnApprove) {
		this.btnApprove = btnApprove;
	}

	public ButtonRound getBtnDecline() {
		return btnDecline;
	}

	public void setBtnDecline(ButtonRound btnDecline) {
		this.btnDecline = btnDecline;
	}

	public ButtonRound getBtnAmend() {
		return btnFeedback;
	}

	public void setBtnAmend(ButtonRound btnAmend) {
		this.btnFeedback = btnAmend;
	}

	public HorizontalPanel getHpnlButtonsApprovar() {
		return hpnlButtonsApprovar;
	}

	public void setHpnlButtonsApprovar(HorizontalPanel hpnlButtonsApprovar) {
		this.hpnlButtonsApprovar = hpnlButtonsApprovar;
	}

	public ButtonRound getBtnDeclineInitiator() {
		return btnDeclineInitiator;
	}

	public void setBtnDeclineInitiator(ButtonRound btnDeclineInitiator) {
		this.btnDeclineInitiator = btnDeclineInitiator;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Label getLblStrategicId() {
		return lblStrategicId;
	}

	public void setLblStrategicId(Label lblStrategicId) {
		this.lblStrategicId = lblStrategicId;
	}

	public VerticalPanel getVpnlRelevantDepartmentPanel() {
		return vpnlRelevantDepartmentPanel;
	}

	public void setVpnlRelevantDepartmentPanel(VerticalPanel vpnlRelevantDepartmentPanel) {
		this.vpnlRelevantDepartmentPanel = vpnlRelevantDepartmentPanel;
	}

	public ScrollPanel getRelevantDepartmentPanel() {
		return relevantDepartmentPanel;
	}

	public void setRelevantDepartmentPanel(ScrollPanel relevantDepartmentPanel) {
		this.relevantDepartmentPanel = relevantDepartmentPanel;
	}

	public Image getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Image submitted) {
		this.submitted = submitted;
	}

}
