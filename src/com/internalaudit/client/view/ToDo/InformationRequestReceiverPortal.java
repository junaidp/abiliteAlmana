package com.internalaudit.client.view.ToDo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.view.PopupsView;
import com.internalaudit.shared.InformationRequestEntity;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class InformationRequestReceiverPortal extends VerticalLayoutContainer {

	protected static final int MIN_HEIGHT = 1;
	protected static final int MIN_WIDTH = 1280;
	protected static final int PREFERRED_HEIGHT = 1;
	protected static final int PREFERRED_WIDTH = 1;
	private ContentPanel panel;
	private static final InformationRequestReceiverProperties properties = GWT
			.create(InformationRequestReceiverProperties.class);
	ListStore<InformationRequestReceiverEntity> store;
	private InternalAuditServiceAsync rpcService;
	private List<InformationRequestReceiverEntity> informationRequests = new ArrayList<InformationRequestReceiverEntity>();
	private VerticalPanel p;
	private PopupsView pp;

	public InformationRequestReceiverPortal(ArrayList<InformationRequestEntity> arrayList) {
		setData(arrayList);
		// setData(exceptions);
		add(createGridFieldWork());
		rpcService = GWT.create(InternalAuditService.class);
	}

	private void setData(ArrayList<InformationRequestEntity> arrayList) {

		for (int i = 0; i < arrayList.size(); i++) {
			// final InformationRequestEntity infoReq = arrayList.get(i);
			InformationRequestReceiverEntity issue = new InformationRequestReceiverEntity();
			// issue.setId(exceptions.get(i).getExceptionId());

			issue.setId(arrayList.get(i).getInformationRequestId());
			issue.setRequestedItem(arrayList.get(i).getRequestItem());
			issue.setRelatedJob(arrayList.get(i).getJob().getJobName());
			issue.setRaisedBy(arrayList.get(i).getAssignedFrom().getEmployeeName());
			issue.setRaiseById(arrayList.get(i).getAssignedFrom().getEmployeeId());
			issue.setRaisedToId(arrayList.get(i).getContactResponsible().getEmployeeId());
			issue.setContactEmail(arrayList.get(i).getContactEmail());
			issue.setSstatus(arrayList.get(i).getStatus());
			issue.setSendNotification(arrayList.get(i).getSendReminder());
			issue.setSendReminder(arrayList.get(i).getSendReminder());
			issue.setRelatedJobId(arrayList.get(i).getJob().getJobCreationId());
			issue.setOverDueDays(arrayList.get(i).getDueDate());
			issue.setInformationRequestLogList(arrayList.get(i).getInformationRequestLogList());
			informationRequests.add(issue);

		}
		// }
	}

	public Widget createGridFieldWork() {
		if (pp != null)
			pp.getPopup().removeFromParent();
		TextButtonCell button = new TextButtonCell();
		ColumnConfig<InformationRequestReceiverEntity, Integer> informationId = new ColumnConfig<InformationRequestReceiverEntity, Integer>(
				properties.id(), 70, "IR#");
		ColumnConfig<InformationRequestReceiverEntity, String> requestedItem = new ColumnConfig<InformationRequestReceiverEntity, String>(
				properties.requestedItem(), 180, "Requested Item");
		ColumnConfig<InformationRequestReceiverEntity, String> informationRaisedBy = new ColumnConfig<InformationRequestReceiverEntity, String>(
				properties.raisedBy(), 150, "Requested By");
		ColumnConfig<InformationRequestReceiverEntity, String> relatedJob = new ColumnConfig<InformationRequestReceiverEntity, String>(
				properties.relatedJob(), 150, "Related Job");
		ColumnConfig<InformationRequestReceiverEntity, Date> informationOverDue = new ColumnConfig<InformationRequestReceiverEntity, Date>(
				properties.overDueDays(), 120, "Due Date");
		Cell cellDueDate = new DateCell(DateTimeFormat.getFormat("MM/dd/yy"));
		informationOverDue.setCell(cellDueDate);
		ColumnConfig<InformationRequestReceiverEntity, String> informationStatus = new ColumnConfig<InformationRequestReceiverEntity, String>(
				properties.status(), 110, "Status");
		ColumnConfig<InformationRequestReceiverEntity, String> viewButton = new ColumnConfig<InformationRequestReceiverEntity, String>(
				properties.viewButton(), 100, "");

		button.setText("view");

		button.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Context c = event.getContext();
				int row = c.getIndex();
				InformationRequestReceiverEntity informationRequest = store.get(row);
				InformationRequestReceiveView infoReceiver = new InformationRequestReceiveView(informationRequest);
				pp = new PopupsView(infoReceiver, "Information Request Receiver");
				// pp.getLabelheading().setText("InformationRequest Receiver");
				// pp.getPopup().setHeadingText("InformationRequest Receiver");
				pp.getVpnlMain().setTitle("Todos");
				pp.getVpnlMain().setWidth("600px");
				pp.getHpnlSPace().setWidth("600px");
				pp.getVpnlMain().setHeight("500px");
//				infoReceiver.getBtnClose().addClickHandler(new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						pp.getVpnlMain().removeFromParent();
//						pp.getPopup().removeFromParent();
//					}
//				});
				// Info.display("Event", "The " + p.getRequestedItem() + " was
				// clicked.");
			}
		});

		viewButton.setCell(button);
		List<ColumnConfig<InformationRequestReceiverEntity, ?>> columns = new ArrayList<ColumnConfig<InformationRequestReceiverEntity, ?>>();
		columns.add(informationId);
		columns.add(requestedItem);
		columns.add(informationRaisedBy);
		columns.add(relatedJob);
		columns.add(informationOverDue);
		columns.add(informationStatus);
		columns.add(viewButton);

		ColumnModel<InformationRequestReceiverEntity> cm = new ColumnModel<InformationRequestReceiverEntity>(columns);

		store = new ListStore<InformationRequestReceiverEntity>(properties.key());
		store.addAll(informationRequests);

		final Grid<InformationRequestReceiverEntity> grid = new Grid<InformationRequestReceiverEntity>(store, cm);
		// grid.setWidth(600);
		grid.getView().setAutoExpandColumn(informationId);
		grid.getView().setForceFit(true);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		p = new VerticalPanel();
		grid.setHeight("220px");
		p.add(grid);

		// VerticalLayoutContainer con = new VerticalLayoutContainer();
		// Anchor anchorView = new Anchor("view");
		// anchorView.getElement().getStyle().setPaddingLeft(820, Unit.PX);
		// con.add(anchorView);

		// con.add(p, new VerticalLayoutData(1, 1));

		// panel = new ContentPanel();
		// panel.setHeight(230);
		// panel.setWidth(850);
		// panel.setHeadingText("InformationRequestReceiver");
		// panel.add(con);
		// return panel;
		// return con;
		return p;
	}

	public void fetchAssignedToIRReLoad() {
		if (pp != null && pp.getVpnlMain() != null) {
			pp.getVpnlMain().removeFromParent();
			pp.getPopup().removeFromParent();
		}
		rpcService.fetchAssignedToIRReLoad(new AsyncCallback<ArrayList<InformationRequestEntity>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("failed to add new Information Request in Updated list");
			}

			@Override
			public void onSuccess(ArrayList<InformationRequestEntity> result) {
				// TODO Auto-generated method stub
				updatedView(result);
				// Window.alert("Successfully added new Information Request in
				// Updated list");
			}
		});
	}

	private void updatedView(ArrayList<InformationRequestEntity> toDosUpdated) {
		store.clear();
		informationRequests.clear();
		p.clear();
		setData(toDosUpdated);
		add(createGridFieldWork());
	}

}