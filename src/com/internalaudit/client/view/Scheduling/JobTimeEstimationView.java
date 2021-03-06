package com.internalaudit.client.view.Scheduling;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.presenter.JobTimeEstimationPresenter.Display;
import com.internalaudit.shared.StrategicDTO;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;

public class JobTimeEstimationView extends Composite implements Display {

	// @UiField ListBox skillSetListBox;

	@UiField
	VerticalLayoutContainer estimatedWeeksContainer;
	@UiField
	VerticalLayoutContainer managementHoursContainer;
	@UiField
	VerticalLayoutContainer travelingDaysContainer;

	@UiField
	TextBox fieldWorkManHoursTextBox;

	@UiField
	TextBox totalWorkingManHoursTextBox;
	@UiField
	HorizontalPanel skillResourceContainer;

	@UiField
	Button saveJobTimeEst;
	@UiField
	TextArea highLevelScopeOfWork;
	@UiField
	ListBox placeofWorkListBox;

	@UiField
	TextBox hoursInclusiveOfTravel;

	@UiField
	Label heading;

	@UiField
	Label areaOfExpertise;

	@UiField
	Label jobTimeEstId;

	@UiField
	Button backButton;

	@UiField
	Label travelingDayslbl;

	private NumberField<Integer> estimatedWeeksTextBox;

	private NumberField<Integer> travelingDaysListBox;

	private NumberField<Integer> managementHoursTextBox;

	private StrategicDTO strategicDTO;

	private boolean listCreated = false;

	private int jobEstimationId;

	private static JobTimeEstimationViewUiBinder uiBinder = GWT.create(JobTimeEstimationViewUiBinder.class);

	interface JobTimeEstimationViewUiBinder extends UiBinder<Widget, JobTimeEstimationView> {
	}

	public JobTimeEstimationView(StrategicDTO dto) {

		initWidget(uiBinder.createAndBindUi(this));
		this.setStrategicDTO(dto);
		initializeTextFields();

	}

	private void initializeTextFields() {
		estimatedWeeksTextBox = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		estimatedWeeksContainer.add(estimatedWeeksTextBox);
		travelingDaysListBox = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		travelingDaysContainer.add(travelingDaysListBox);
		managementHoursTextBox = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
		managementHoursContainer.add(managementHoursTextBox);
		managementHoursTextBox.setText("0");
		estimatedWeeksTextBox.setText("0");
		travelingDaysListBox.setText("0");
		totalWorkingManHoursTextBox.setText("0");
		hoursInclusiveOfTravel.setText("0");
	}

	@Override
	public NumberField<Integer> getEstWeeksTextBox() {

		return estimatedWeeksTextBox;
	}

	@Override
	public TextBox getFieldWorkManHours() {
		return fieldWorkManHoursTextBox;
	}

	@Override
	public NumberField<Integer> getMgmtHours() {
		return managementHoursTextBox;
	}

	@Override
	public TextBox getTotalWorkingManHours() {
		return totalWorkingManHoursTextBox;
	}

	public HorizontalPanel getSkillsResourceContainer() {
		return skillResourceContainer;
	}

	public void setSkillsResourceContainer(HorizontalPanel skillResourceContainer) {
		this.skillResourceContainer = skillResourceContainer;
	}

	public Button getSaveJobTimeEst() {
		return saveJobTimeEst;
	}

	public void setSaveJobTimeEst(Button saveJobTimeEst) {
		this.saveJobTimeEst = saveJobTimeEst;
	}

	public TextBox getTotalWorkingManHoursTextBox() {
		return totalWorkingManHoursTextBox;
	}

	public HorizontalPanel getSkillResourceContainer() {
		return skillResourceContainer;
	}

	public TextArea getHighLevelScopeOfWork() {
		return highLevelScopeOfWork;
	}

	public ListBox getPlaceofWorkListBox() {
		return placeofWorkListBox;
	}

	public NumberField<Integer> getTravelingDaysTextBox() {
		return travelingDaysListBox;
	}

	public TextBox getHoursInclusiveOfTravel() {
		return hoursInclusiveOfTravel;
	}

	public StrategicDTO getStrategicDTO() {
		return strategicDTO;
	}

	public void setStrategicDTO(StrategicDTO strategicDTO) {
		this.strategicDTO = strategicDTO;
	}

	public Label getHeading() {
		return heading;
	}

	public void setHeading(Label heading) {
		this.heading = heading;
	}

	public Button getBackButton() {
		return backButton;
	}

	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

	public Label getAreaOfExpertise() {
		return areaOfExpertise;
	}

	public void setAreaOfExpertise(Label areaOfExpertise) {
		this.areaOfExpertise = areaOfExpertise;
	}

	public boolean isListCreated() {
		return listCreated;
	}

	public void setListCreated(boolean listCreated) {
		this.listCreated = listCreated;
	}

	@Override
	public void setJobEstimationId(int jobTimeEstimationId) {
		this.jobEstimationId = jobTimeEstimationId;

	}

	public int getJobEstimationId() {
		return this.jobEstimationId;

	}

	public Label getJobTimeEstId() {
		return jobTimeEstId;
	}

	public void setJobTimeEstId(Label jobTimeEstId) {
		this.jobTimeEstId = jobTimeEstId;
	}

	public void disableFields() {
		estimatedWeeksTextBox.setEnabled(false);
		fieldWorkManHoursTextBox.setEnabled(false);
		managementHoursTextBox.setEnabled(false);
		totalWorkingManHoursTextBox.setEnabled(false);

		saveJobTimeEst.setVisible(false);
		highLevelScopeOfWork.setEnabled(false);
		placeofWorkListBox.setEnabled(false);
		travelingDaysListBox.setEnabled(false);
		hoursInclusiveOfTravel.setEnabled(false);
	}

	public Label getTravelingDayslbl() {
		return travelingDayslbl;
	}

	public void setTravelingDayslbl(Label travelingDayslbl) {
		this.travelingDayslbl = travelingDayslbl;
	}

}
