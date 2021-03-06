package com.internalaudit.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.internalaudit.client.view.AuditEngagement.LabelHeading;
import com.internalaudit.shared.InternalAuditConstants;

public class AuditUniverseStrategicViewHeading extends Composite {

	public AuditUniverseStrategicViewHeading() {

		final HorizontalPanel hpnlStrategic = new HorizontalPanel();
		initWidget(hpnlStrategic);
		// hpnlStrategic.setHeight("40px");
		hpnlStrategic.setSize("1000px", "100%");
		// hpnl.setSpacing(5);
		LabelHeading lblStrategicObjective = new LabelHeading();
		lblStrategicObjective.setText(InternalAuditConstants.STRATEGICOBJECTIVE);
		// lblStrategicObjective.getElement().getStyle().setFontSize(16,
		// Unit.PX);
		//Label lblObjectiveOwner = new Label(InternalAuditConstants.STRATEGICOBJECTIVEOWNER);
		// lblObjectiveOwner.getElement().getStyle().setFontSize(16, Unit.PX);
		LabelHeading relevantDivision = new LabelHeading();
		relevantDivision.setText(InternalAuditConstants.STRATEGICLOCATION);
		LabelHeading relevantDepartment = new LabelHeading();
		relevantDepartment.setText(InternalAuditConstants.STRATEGICDEPT);
		// relevantDepartment.getElement().getStyle().setFontSize(16, Unit.PX);
		Label objectiveAchievementDate = new Label(InternalAuditConstants.STRATEGICDATE);
		LabelHeading objectiveId = new LabelHeading();
		// objectiveId.addStyleName("w3-container w3-left");
		objectiveId.setText(AuditConstants.OBJECTIVEID);

		// objectiveId.getElement().getStyle().setFontSize(16, Unit.PX);
		// objectiveAchievementDate.getElement().getStyle().setFontSize(16,
		// Unit.PX);
		// objectiveId.setHeight("20px");

		hpnlStrategic.add(objectiveId);
		objectiveId.getElement().getStyle().setMarginRight(3, Unit.PX);
		objectiveId.setWidth("35px");
		hpnlStrategic.addStyleName("statusRowStrategic");
		hpnlStrategic.add(lblStrategicObjective);
		// hpnlStrategic.add(lblObjectiveOwner);
		hpnlStrategic.add(relevantDivision);
		hpnlStrategic.add(relevantDepartment);

		hpnlStrategic.setWidth("100%");
		// lblStrategicObjective.setWidth("553px");
		// lblStrategicObjective.addStyleName("labelspace");
		//lblObjectiveOwner.setWidth("180px");
		relevantDivision.setWidth("220px");
		relevantDepartment.setWidth("230px");
		objectiveId.setWordWrap(false);
		objectiveAchievementDate.setWordWrap(false);
		// lblStrategicObjective.addStyleName("labelTitle");
		// lblObjectiveOwner.addStyleName("labelTitle");
		// relevantDepartment.addStyleName("labelTitle");
		objectiveAchievementDate.addStyleName("labelTitle");
		// objectiveId.addStyleName("labelTitle");
		lblStrategicObjective.addStyleName("labelspace");
		lblStrategicObjective.setWidth("498px");
		// objectiveId.addStyleName("white");
	}

}
