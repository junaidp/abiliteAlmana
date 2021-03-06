package com.internalaudit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.view.data.RiskAssesmentStrategicViewData;
import com.sencha.gxt.widget.core.client.form.TextArea;

public class RiskAssesmentStrategicView extends Composite {

	private static RiskAssesmentStrategicViewUiBinder uiBinder = GWT.create(RiskAssesmentStrategicViewUiBinder.class);

	interface RiskAssesmentStrategicViewUiBinder extends UiBinder<Widget, RiskAssesmentStrategicView> {

	}

	private String strategicObjective;
	@UiField
	ListBox overallRating;
	@UiField
	HorizontalPanel userOption;
	@UiField
	ListBox listBoxUserOption;
	@UiField
	VerticalPanel riskFactors;
	// @UiField
	// VerticalPanel vpnlComments; //commented by moqeet
	// @UiField Button save;ssss
	// @UiField TextField comments;
	// @UiField Button amend;
	@UiField
	HorizontalPanel hpnlButtons;
	@UiField
	HorizontalPanel panelRatingComment;
	private Label feedback = new Label(" Feedback ");
	private Image submitted = new Image(" images/tick.png ");
	private Label lblImg = new Label("  ");

	private Button btnSave = new Button("Save");
	private Button btnSubmit = new Button("Submit");
	private Button btnApprove = new Button("Approve");
	private Button btnDecline = new Button("Delete");
	private Button btnDeclineInitiator = new Button("Delete");
	private Button btnAmend = new Button("Feedback");
	private HorizontalPanel hpnlButtonInitiator = new HorizontalPanel();
	private HorizontalPanel hpnlButtonsApprovar = new HorizontalPanel();
	private int index;
	private int strategicId;
	private String comment;
	private Label lblComment;
	private TextArea ratingComment;

	private RiskAssesmentStrategicViewData riskAssesmentStrategicViewData = new RiskAssesmentStrategicViewData();

	public RiskAssesmentStrategicView() {
		initWidget(uiBinder.createAndBindUi(this));
		lblComment = new Label("Comment");
		lblComment.setWidth("250px");
		lblComment.addStyleName("boldText");
		lblComment.getElement().getStyle().setPaddingTop(10, Unit.PX);
		panelRatingComment.add(lblComment);
		ratingComment = new TextArea();
		ratingComment.setSize("600px", "40px");
		// placeholder not working
		// txtAreaComment.getElement().setPropertyString("placeholder", "Enter
		// your Comment here");
		panelRatingComment.add(ratingComment);
		panelRatingComment.setVisible(false);
		hpnlButtons.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		// comments.setEmptyText("Enter Comments");
		// amend.setHeight("25px");
		// comments.setHeight("100px");
		submitted.addStyleName("pointer");
		submitted.setTitle("submitted");
		submitted.setVisible(true);

		lblImg.setVisible(true);
		lblImg.setWidth("12px");
		hpnlButtons.add(hpnlButtonInitiator);
		hpnlButtons.add(hpnlButtonsApprovar);
		HorizontalPanel hpnlSpace = new HorizontalPanel();
		HorizontalPanel hpnlSpaceApprovar = new HorizontalPanel();

		hpnlButtonsApprovar.add(hpnlSpaceApprovar);
		hpnlButtonsApprovar.add(btnAmend);
		hpnlButtonsApprovar.add(btnApprove);
		hpnlButtonsApprovar.setSpacing(2);

		hpnlButtonsApprovar.setVisible(false);
		hpnlButtonInitiator.setVisible(false);
		// btnDecline.setWidth("70px");
		// btnAmend.setWidth("70px");
		// btnApprove.setWidth("70px");

		hpnlSpace.setWidth("850px");
		hpnlSpaceApprovar.setWidth("850px");
		hpnlButtonInitiator.add(hpnlSpace);
		hpnlButtonInitiator.add(feedback);// added by moqeet
		hpnlButtonInitiator.add(btnDeclineInitiator);
		btnDeclineInitiator.setVisible(false);
		hpnlButtonInitiator.add(btnSave);
		hpnlButtonInitiator.add(btnSubmit);
		// btnSave.setWidth("70px");
		// btnSubmit.setWidth("70px");
		// btnDeclineInitiator.setWidth("70px");
		hpnlButtonInitiator.setSpacing(2);
		// HorizontalPanel hpnlComments = new HorizontalPanel();

		// hpnlComments.add(feedback);
		// hpnlComments.add(submitted); change here
		// vpnlComments.add(hpnlComments);
		// hpnlComments.setSpacing(5);

		// vpnlComments.add(comments);
		// hpnlStrategic.setWidth("900px");
		listBoxUserOption.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (!overallRating.getSelectedValue().equalsIgnoreCase(listBoxUserOption.getSelectedValue())) {
					panelRatingComment.setVisible(true);
				} else {
					panelRatingComment.setVisible(false);
				}
			}
		});
	}

	public RiskAssesmentStrategicViewData getRiskAssesmentStrategicViewData() {
		return riskAssesmentStrategicViewData;
	}

	public void setRiskAssesmentStrategicViewData(RiskAssesmentStrategicViewData riskAssesmentStrategicViewData) {
		this.riskAssesmentStrategicViewData = riskAssesmentStrategicViewData;
	}

	public String getStrategicObjective() {
		return strategicObjective;
	}

	public void setStrategicObjective(String strategicObjective) {
		this.strategicObjective = strategicObjective;
	}

	public VerticalPanel getRiskFactors() {
		return riskFactors;
	}

	public void setRiskFactors(VerticalPanel riskFactors) {
		this.riskFactors = riskFactors;
	}

	// public Button getSave() {
	// return save;
	// }
	//
	// public void setSave(Button save) {
	// this.save = save;
	// }

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Button getBtnSubmit() {
		return btnSubmit;
	}

	public void setBtnSubmit(Button btnSubmit) {
		this.btnSubmit = btnSubmit;
	}

	public Button getBtnApprove() {
		return btnApprove;
	}

	public void setBtnApprove(Button btnApprove) {
		this.btnApprove = btnApprove;
	}

	public Button getBtnDecline() {
		return btnDecline;
	}

	public void setBtnDecline(Button btnDecline) {
		this.btnDecline = btnDecline;
	}

	public Button getBtnDeclineInitiator() {
		return btnDeclineInitiator;
	}

	public void setBtnDeclineInitiator(Button btnDeclineInitiator) {
		this.btnDeclineInitiator = btnDeclineInitiator;
	}

	public Button getBtnAmend() {
		return btnAmend;
	}

	public void setBtnAmend(Button btnAmend) {
		this.btnAmend = btnAmend;
	}

	public HorizontalPanel getHpnlButtonInitiator() {
		return hpnlButtonInitiator;
	}

	public void setHpnlButtonInitiator(HorizontalPanel hpnlButtonInitiator) {
		this.hpnlButtonInitiator = hpnlButtonInitiator;
	}

	public HorizontalPanel getHpnlButtonsApprovar() {
		return hpnlButtonsApprovar;
	}

	public void setHpnlButtonsApprovar(HorizontalPanel hpnlButtonsApprovar) {
		this.hpnlButtonsApprovar = hpnlButtonsApprovar;
	}

	public int getStrategicId() {
		return strategicId;
	}

	public void setStrategicId(int strategicId) {
		this.strategicId = strategicId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Label getComments() {
		return feedback;
	}

	public void setComments(Label comments) {
		this.feedback = comments;
	}

	public Image getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Image submitted) {
		this.submitted = submitted;
	}

	public ListBox getListBoxUserOption() {
		return listBoxUserOption;
	}

	public void setListBoxUserOption(ListBox listBoxUserOption) {
		this.listBoxUserOption = listBoxUserOption;
	}

	public Label getLblImg() {
		return lblImg;
	}

	public void setLblImg(Label lblImg) {
		this.lblImg = lblImg;
	}

	public HorizontalPanel getPanelRatingComment() {
		return panelRatingComment;
	}

	public void setPanelRatingComment(HorizontalPanel panelRatingComment) {
		this.panelRatingComment = panelRatingComment;
	}

	public Label getLblComment() {
		return lblComment;
	}

	public void setLblComment(Label lblComment) {
		this.lblComment = lblComment;
	}

	public TextArea getRatingComment() {
		return ratingComment;
	}

	public void setRatingComment(TextArea ratingComment) {
		this.ratingComment = ratingComment;
	}

	public ListBox getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(ListBox overallRating) {
		this.overallRating = overallRating;
	}

	// public TextField getComments() {
	// return comments;
	// }
	//
	// public void setComments(TextField comments) {
	// this.comments = comments;
	// }
	//
	// public Button getAmend() {
	// return amend;
	// }
	//
	// public void setAmend(Button amend) {
	// this.amend = amend;
	// }

}
