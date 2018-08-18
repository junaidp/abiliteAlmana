package com.internalaudit.client.view.AuditEngagement;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.internalaudit.client.util.MyUtil;
import com.internalaudit.shared.ActivityObjective;
import com.internalaudit.shared.RiskObjective;
import com.internalaudit.shared.SuggestedControls;

public class RiskControlMatrixView extends VerticalPanel {
	//Label lblactivityObjective = new Label("Activity Objective");
	Label lblrisk = new Label("Risks");
	Label lblriskRatings = new Label("Inherent Risk ");
	Label lblcontrol = new Label("Controls");
	//	Label lblapplicability = new Label("Applicability");
	Label lblresidualRisk = new Label("Control Risk");
	Label lblRefData = new Label("");
	Label lblReferenceNo = new Label("Reference Number");
	Image imgRating = new Image();
	Image imgRatingControl = new Image();
	// data labels
	//Label lblactivitydata = new Label("");
	Label lblriskdata = new Label("");
	private TextArea txtAreaControl = new TextArea();
	private RiskObjective riskObjective = new RiskObjective();
	//CheckBox checkBoxApplicability = new CheckBox("");
	// private AddIcon btnAdd = new AddIcon();
	ListBox listBoxInherintRating = new ListBox();
	private ListBox listBoxRisk = new ListBox();
	ListBox listBoxControlRating = new ListBox();
	private int suggestedControlsId = 0;
	private Button btnSelect = new Button("Select");


	//Image delete = new Image("images/deleteIcon.png");

	public RiskControlMatrixView() {
		listBoxInherintRating.setEnabled(false);

		listBoxRisk.setVisible(false);
		//lblName.addStyleName("w3-panel w3-light-blue");

		lblReferenceNo.addStyleName("w3-panel w3-light-blue");

		//	lblactivityObjective.addStyleName("w3-panel w3-light-blue");

		lblrisk.addStyleName("w3-panel w3-light-blue");

		lblcontrol.addStyleName("w3-panel w3-light-blue");

		lblriskRatings.addStyleName("w3-panel w3-light-blue");

		//lblapplicability.addStyleName("w3-panel w3-light-blue");

		lblresidualRisk.addStyleName("w3-panel w3-light-blue");


		// all the data views are defined here data from db

		//	lblactivitydata.setHeight("90px");
		//	lblactivitydata.setWidth("200px");

		lblriskdata.setHeight("90px");
		lblriskdata.setWidth("200px");


		txtAreaControl.setWidth("400px");
		txtAreaControl.setHeight("90px");
		txtAreaControl.setText("");

		listBoxInherintRating.setWidth("100px");
		listBoxInherintRating.addItem("Low","0");
		listBoxInherintRating.addItem("Medium","1");
		listBoxInherintRating.addItem("High","2");

		listBoxControlRating.addItem("Low" ,"0");
		listBoxControlRating.addItem("Medium" ,"1");
		listBoxControlRating.addItem("High" ,"2");

		//all the styling defined here

		//lblactivityObjective.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lblrisk.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lblcontrol.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lblriskRatings.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lblriskRatings.setWidth("150px");
		//lblapplicability.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lblresidualRisk.getElement().getStyle().setFontWeight(FontWeight.BOLD); 
		lblresidualRisk.setWidth("140px");
		lblRefData.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lblReferenceNo.getElement().getStyle().setFontWeight(FontWeight.BOLD);

		//	lblactivityObjective.getElement().getStyle().setMarginLeft(20, Unit.PX);
		lblrisk.getElement().getStyle().setMarginLeft(20, Unit.PX);
		lblRefData.getElement().getStyle().setMarginLeft(20, Unit.PX);
		lblReferenceNo.getElement().getStyle().setMarginLeft(20, Unit.PX);
		lblcontrol.getElement().getStyle().setMarginLeft(20, Unit.PX);
		lblriskRatings.getElement().getStyle().setMarginLeft(20, Unit.PX);
		//		lblapplicability.getElement().getStyle().setMarginLeft(20, Unit.PX);
		//		lblapplicability.setWidth("120px");
		lblresidualRisk.getElement().getStyle().setMarginLeft(20, Unit.PX);

		//	lblactivitydata.getElement().getStyle().setMarginLeft(20, Unit.PX);


		lblriskdata.getElement().getStyle().setMarginLeft(20, Unit.PX);
		txtAreaControl.getElement().getStyle().setMarginLeft(20, Unit.PX);
		listBoxInherintRating.getElement().getStyle().setMarginLeft(40, Unit.PX);
		//checkBoxApplicability.getElement().getStyle().setMarginLeft(60, Unit.PX);
		listBoxControlRating.getElement().getStyle().setMarginLeft(60, Unit.PX);

		// making flexwidget and adding labels in flex widget

		FlexTable flex = new FlexTable();
		VerticalPanel vpLblRef = new VerticalPanel();
		vpLblRef.setWidth("150px");
		flex.setWidget(0,3, lblReferenceNo);
		vpLblRef.add(lblRefData);
		flex.setWidget(1,3, vpLblRef);
		//  flex.setWidget(0,1,actv);

		//flex.setWidget(0,0, lblactivityObjective);
		VerticalPanel vpLblactivitydata = new VerticalPanel();
		//vpLblactivitydata.add(lblactivitydata);
		vpLblactivitydata.setWidth("220px");
		//flex.setWidget(1,0,vpLblactivitydata);

		flex.setWidget(0,0, lblrisk);
		VerticalPanel vpLblRisk = new VerticalPanel();
		vpLblRisk.setWidth("220px");
		vpLblRisk.add(listBoxRisk);
		vpLblRisk.add(lblriskdata);
		flex.setWidget(1,0,vpLblRisk);

		flex.setWidget(0,3, lblcontrol);
		VerticalPanel vpLblControl = new VerticalPanel();
		vpLblControl.setWidth("230px");
		vpLblControl.add(txtAreaControl);
		flex.setWidget(1,3,vpLblControl);
		//flex.setWidget(2,4,btnAdd);

		flex.setWidget(0,1, lblriskRatings);
		HorizontalPanel vpLblRiskRating = new HorizontalPanel();
		vpLblRiskRating.setWidth("230px");
		vpLblRiskRating.add(listBoxInherintRating);
		vpLblRiskRating.add(imgRating);
		flex.setWidget(1,1,vpLblRiskRating);
		//lblapplicability.setWidth("130px");
		//	flex.setWidget(0,3, lblapplicability);
		VerticalPanel vpLblApplicability = new VerticalPanel();
		vpLblApplicability.setWidth("170px");
		//	vpLblApplicability.add(checkBoxApplicability);
		//	flex.setWidget(1,3,vpLblApplicability);

		flex.setWidget(0,4, lblresidualRisk);
		HorizontalPanel panelResidualRating = new HorizontalPanel();
		panelResidualRating.setWidth("180px");
		panelResidualRating.add(listBoxControlRating);
		panelResidualRating.add(imgRatingControl);
		flex.setWidget(1,4,panelResidualRating);
		flex.setWidget(1,5,btnSelect);


		add(flex);
		setImage(Integer.parseInt(listBoxInherintRating.getValue(listBoxInherintRating.getSelectedIndex())), imgRating);
		setImage(Integer.parseInt(listBoxControlRating.getValue(listBoxControlRating.getSelectedIndex())), imgRatingControl);
		listBoxControlRating.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setImage(Integer.parseInt(listBoxControlRating.getValue(listBoxControlRating.getSelectedIndex())), imgRatingControl);

			}
		});
		
		listBoxInherintRating.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setImage(Integer.parseInt(listBoxInherintRating.getValue(listBoxInherintRating.getSelectedIndex())), imgRating);

			}
		});

		

	}	

	public void hideElemetns(){


		//	lblapplicability.setVisible(false);
		//	lblactivityObjective.setVisible(false);
		lblrisk.setVisible(false);
		lblriskRatings.setVisible(false);
		lblcontrol.setVisible(false);
		lblriskdata.setVisible(false);
		lblcontrol.setVisible(false);
		lblresidualRisk.setVisible(false);
		//	lblapplicability.setVisible(false);
		lblcontrol.setVisible(false);


		//lblactivitydata.setVisible(false);

		listBoxInherintRating.setVisible(false);
	}

	public void setData(RiskControlMatrixView controlView){
		suggestedControlsId= controlView.getSuggestedControlsId();
		txtAreaControl.setText(controlView.getTxtAreaControl().getText());
		lblRefData.setText(MyUtil.getRandom());
	
		for(int i=0; i< listBoxInherintRating.getItemCount(); i++){
			if(listBoxInherintRating.getValue(i).equals(controlView.getListBoxInherintRating().getSelectedValue())){
				listBoxInherintRating.setSelectedIndex(i);
				break;
			}
		}
		
		for(int i=0; i< listBoxControlRating.getItemCount(); i++){
			if(listBoxControlRating.getValue(i).equals(controlView.getListBoxControlRating().getSelectedValue())){
				listBoxControlRating.setSelectedIndex(i);
				break;
			}
		}
		
		for(int i=0; i< listBoxRisk.getItemCount(); i++){
			if(listBoxRisk.getValue(i).equals(controlView.getListBoxRisk().getSelectedValue())){
				listBoxRisk.setSelectedIndex(i);
				break;
			}
		}
		setImage(Integer.parseInt(listBoxInherintRating.getValue(listBoxInherintRating.getSelectedIndex())), imgRating);
		setImage(Integer.parseInt(listBoxControlRating.getValue(listBoxControlRating.getSelectedIndex())), imgRatingControl);
		
		
	}

	public void setData(SuggestedControls suggestedControls, boolean riskAdded) {
		txtAreaControl.setText(suggestedControls.getSuggestedControlsName());
		suggestedControlsId = suggestedControls.getSuggestedControlsId();
		// check its right or not..?
		//	lblactivitydata.setText(suggestedControls.getRiskId().getObjectiveId().getObjectiveName());
		lblriskdata.setText(suggestedControls.getRiskId().getRiskname());
		// TODO Populate all fields, whatever is missing ,add in db
		lblRefData.setText(suggestedControls.getSuggestedReferenceNo());
		//listBoxRating.setse.setItemText(1, riskObjective.getRiskRating());

		//for control list box
		for(int i=0; i< listBoxControlRating.getItemCount(); i++){
			if(Integer.parseInt(listBoxControlRating.getValue(i)) == suggestedControls.getControlRisk()){
				listBoxControlRating.setSelectedIndex(i);
				setImage(Integer.parseInt(listBoxControlRating.getValue(i)), imgRatingControl);
			}
		}

		// for inherent listbox
		for(int i=0; i< listBoxInherintRating.getItemCount(); i++){
			if(Integer.parseInt(listBoxInherintRating.getValue(i)) == suggestedControls.getRiskId().getRiskRating()){
				listBoxInherintRating.setSelectedIndex(i);

				setImage(Integer.parseInt(listBoxInherintRating.getValue(i)), imgRating);

			}
		}
		//checkBoxApplicability.setChecked(suggestedControls.getChecked());

		if(riskAdded){
			lblriskdata.setVisible(false);	
			lblriskRatings.setVisible(false);
			//	lblactivityObjective.setVisible(false);
			lblrisk.setVisible(false);
			//	lblactivitydata.setVisible(false);
			listBoxInherintRating.setVisible(false);
			lblReferenceNo.setVisible(false);
			imgRating.setVisible(false);
			//	lblapplicability.setVisible(false);
			lblcontrol.setVisible(false);
			lblresidualRisk.setVisible(false);

		}


	}

	private void setImage(int value, Image imgRatingControl) {
		if(value == 2){
			imgRatingControl.setUrl("redcircle.png");
		}
		if(value == 1){
			imgRatingControl.setUrl("yellowcircle.png");
		}
		if(value == 0){
			imgRatingControl.setUrl("greencircle.png");
		}

	}

	public void getData(SuggestedControls suggestedControls) {
		suggestedControls.setRiskId(riskObjective);
		suggestedControls.setSuggestedControlsId(suggestedControlsId);
		suggestedControls.setSuggestedControlsName(txtAreaControl.getText());
		suggestedControls.setSuggestedReferenceNo(lblRefData.getText());
		suggestedControls.setControlRisk(Integer.parseInt(listBoxControlRating.getValue(listBoxControlRating.getSelectedIndex())));
		suggestedControls.getRiskId().setRiskRating(Integer.parseInt(listBoxInherintRating.getValue(listBoxInherintRating.getSelectedIndex())));

	}

	protected void populateRisks(final ArrayList<RiskObjective> risks, RiskObjective savedRiskObjective){

		for(int i=0; i< risks.size(); i++){
			listBoxRisk.addItem(risks.get(i).getRiskReferenceNo(), risks.get(i).getRiskId()+"");

		}
		if(savedRiskObjective == null){
			lblriskdata.setText(risks.get(0).getRiskname());
			riskObjective = risks.get(0);
		
		}else{
			for(int i =0; i< listBoxRisk.getItemCount(); i++){
				if(Integer.parseInt(listBoxRisk.getValue(i)) == savedRiskObjective.getRiskId()){
					listBoxRisk.setSelectedIndex(i);
					riskObjective = savedRiskObjective;
					lblriskdata.setText(savedRiskObjective.getRiskname());
					
				}
			}
		}


		listBoxRisk.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setRiskObjectiveAgaintRiskId(risks, Integer.parseInt(listBoxRisk.getSelectedValue()));
				lblriskdata.setText(riskObjective.getRiskname());
			
				for(int i=0; i< listBoxInherintRating.getItemCount(); i++){
					if(Integer.parseInt(listBoxInherintRating.getValue(i)) == riskObjective.getRiskRating()){
						listBoxInherintRating.setSelectedIndex(i);
						setImage(Integer.parseInt(listBoxInherintRating.getSelectedValue()), imgRating);

					}
				}
			}
		});
	}

	private void setRiskObjectiveAgaintRiskId(ArrayList<RiskObjective> risks, int value){
		
		for(int i=0 ; i< risks.size(); i++){
			if(risks.get(i).getRiskId() == value){
				riskObjective = risks.get(i);
				
			}

		}
		

	}

	public RiskObjective getRiskObjective() {
		return riskObjective;
	}

	public void setRiskObjective(RiskObjective riskObjective) {
		this.riskObjective = riskObjective;
	}

	public Button getBtnSelect() {
		return btnSelect;
	}

	public void setBtnSelect(Button btnSelect) {
		this.btnSelect = btnSelect;
	}

	public Label getLblriskdata() {
		return lblriskdata;
	}

	public TextArea getTxtAreaControl() {
		return txtAreaControl;
	}

	public int getSuggestedControlsId() {
		return suggestedControlsId;
	}

	public void setSuggestedControlsId(int suggestedControlsId) {
		this.suggestedControlsId = suggestedControlsId;
	}

	public ListBox getListBoxRisk() {
		return listBoxRisk;
	}

	public void setListBoxRisk(ListBox listBoxRisk) {
		this.listBoxRisk = listBoxRisk;
	}

	public Label getLblRefData() {
		return lblRefData;
	}

	public ListBox getListBoxInherintRating() {
		return listBoxInherintRating;
	}

	public ListBox getListBoxControlRating() {
		return listBoxControlRating;
	}

	public void disableFields() {
		listBoxInherintRating.setEnabled(false);
		listBoxControlRating.setEnabled(false);
		txtAreaControl.setEnabled(false);
		listBoxRisk.setVisible(false);
		
	}

	public void enableFields() {
		
		listBoxInherintRating.setEnabled(true);
		listBoxControlRating.setEnabled(true);
		txtAreaControl.setEnabled(true);
		listBoxRisk.setVisible(true);
	}

}