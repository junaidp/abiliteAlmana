
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
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.view.DisplayAlert;
import com.internalaudit.client.view.PopupsView;
import com.internalaudit.client.view.ToDoView;
import com.internalaudit.shared.Employee;
import com.internalaudit.shared.JobCreation;
import com.internalaudit.shared.ToDo;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class ToDoRaiserPortal extends VerticalLayoutContainer {

	protected static final int MIN_HEIGHT = 1;
	protected static final int MIN_WIDTH = 1280;
	protected static final int PREFERRED_HEIGHT = 1;
	protected static final int PREFERRED_WIDTH = 1;
	private ContentPanel panel;
	private static final ToDoRaiserProperties properties = GWT.create(ToDoRaiserProperties.class);

	ListStore<ToDoRaiserEntity> store;
	private List<ToDoRaiserEntity> toDos = new ArrayList<ToDoRaiserEntity>();
	private InternalAuditServiceAsync rpcService;
	VerticalPanel con = new VerticalPanel();
	private PopupsView pp;
	private PopupsView popUpToDoRaiserFinalView;

	public ToDoRaiserPortal(ArrayList<ToDo> arrayListToDos) {
		setData(arrayListToDos);
		// setData(exceptions);
		add(createGridFieldWork());
		rpcService = GWT.create(InternalAuditService.class);
	}

	private void setData(ArrayList<ToDo> arrayList) {
		for (int i = 0; i < arrayList.size(); i++) {
			ToDoRaiserEntity issue = new ToDoRaiserEntity();

			issue.setId(arrayList.get(i).getToDoId());
			issue.setTaskName(arrayList.get(i).getTask());
			issue.setTaskDescription(arrayList.get(i).getDescription());
			// task added and description commented by moqeet
			issue.setRelatedJob(arrayList.get(i).getJob().getJobName());
			issue.setRaisedTo(arrayList.get(i).getAssignedTo().getEmployeeName());
			issue.setRaisedBy(arrayList.get(i).getAssignedFrom().getEmployeeName());
			issue.setOverDueDays(arrayList.get(i).getDueDate());
			issue.setStatus(arrayList.get(i).getRespond());
			issue.setReply(arrayList.get(i).getRespond());
			issue.setRaisedById(arrayList.get(i).getAssignedFrom().getEmployeeId());
			issue.setRaisedToId(arrayList.get(i).getAssignedTo().getEmployeeId());
			issue.setRelatedJobId(arrayList.get(i).getJob().getJobCreationId());
			issue.setTodoLogList(arrayList.get(i).getTodosLogList());

			toDos.add(issue);
		}
	}

	public Widget createGridFieldWork() {

		if (popUpToDoRaiserFinalView != null)
			popUpToDoRaiserFinalView.getPopup().removeFromParent();
		TextButtonCell button = new TextButtonCell();
		ColumnConfig<ToDoRaiserEntity, Integer> informationId = new ColumnConfig<ToDoRaiserEntity, Integer>(
				properties.id(), 70, "SR#");
		ColumnConfig<ToDoRaiserEntity, String> requestedItem = new ColumnConfig<ToDoRaiserEntity, String>(
				properties.taskName(), 180, "Task");
		ColumnConfig<ToDoRaiserEntity, String> informationRaisedBy = new ColumnConfig<ToDoRaiserEntity, String>(
				properties.raisedTo(), 140, "Asigned To");
		ColumnConfig<ToDoRaiserEntity, String> relatedJob = new ColumnConfig<ToDoRaiserEntity, String>(
				properties.relatedJob(), 140, " Job");
		ColumnConfig<ToDoRaiserEntity, Date> informationOverDue = new ColumnConfig<ToDoRaiserEntity, Date>(
				properties.overDueDays(), 110, "Due Date");
		Cell cellDueDate = new DateCell(DateTimeFormat.getFormat("MM/dd/yy"));
		informationOverDue.setCell(cellDueDate);
		ColumnConfig<ToDoRaiserEntity, String> informationStatus = new ColumnConfig<ToDoRaiserEntity, String>(
				properties.status(), 100, "Status");
		ColumnConfig<ToDoRaiserEntity, String> viewButton = new ColumnConfig<ToDoRaiserEntity, String>(
				properties.viewButton(), 100, "");

		button.setText("view");

		button.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				Context c = event.getContext();
				int row = c.getIndex();
				ToDoRaiserEntity toDo = store.get(row);
				ToDoRaiserFinalView toDoRaiser = new ToDoRaiserFinalView(toDo);
				popUpToDoRaiserFinalView = new PopupsView(toDoRaiser, "To Do Raiser");
				popUpToDoRaiserFinalView.hideCloseBtn();
				// pp.getLabelheading().setText("ToDo Receiver Receiver");
				// pp.getPopup().setHeadingText("ToDo Receiver");
				popUpToDoRaiserFinalView.getVpnlMain().setTitle("Todos");
				popUpToDoRaiserFinalView.getVpnlMain().setWidth("600px");
				popUpToDoRaiserFinalView.getHpnlSPace().setWidth("600px");
				popUpToDoRaiserFinalView.getVpnlMain().setHeight("500px");

				toDoRaiser.getBtnCancel().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						popUpToDoRaiserFinalView.getVpnlMain().removeFromParent();
						popUpToDoRaiserFinalView.getPopup().removeFromParent();

					}
				});
				// Info.display("Event", "The " + p.getRequestedItem() + " was
				// clicked.");
			}
		});

		viewButton.setCell(button);
		List<ColumnConfig<ToDoRaiserEntity, ?>> columns = new ArrayList<ColumnConfig<ToDoRaiserEntity, ?>>();
		columns.add(informationId);
		columns.add(requestedItem);
		columns.add(informationRaisedBy);
		columns.add(relatedJob);
		columns.add(informationOverDue);
		columns.add(informationStatus);
		columns.add(viewButton);

		ColumnModel<ToDoRaiserEntity> cm = new ColumnModel<ToDoRaiserEntity>(columns);

		store = new ListStore<ToDoRaiserEntity>(properties.key());
		store.addAll(toDos);

		final Grid<ToDoRaiserEntity> grid = new Grid<ToDoRaiserEntity>(store, cm);
		// grid.setWidth(600);
		grid.getView().setAutoExpandColumn(informationId);
		grid.getView().setForceFit(true);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		// VerticalPanel p = new VerticalPanel();
		// p.setHeight("220px");
		// p.add(grid);

		// VerticalLayoutContainer con = new VerticalLayoutContainer();

		Anchor addTask = new Anchor("Add New Task");
		addTask.addStyleName("w3-right");
		con.add(addTask);

		addTask.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final ToDoView todoview = new ToDoView();
				pp = new PopupsView(todoview, "To Do");
				pp.hideCloseBtn();
				// pp.getLabelheading().setText("To Do");
				// pp.getPopup().setHeadingText("To Do");
				pp.getVpnlMain().setWidth("370px");
				pp.getHpnlSPace().setWidth("370px");
				pp.getVpnlMain().setHeight("320px");

				pp.getClose().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						todoview.deleteUnSavedAttachments();

					}

				});

				todoview.getBtnCancel().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						pp.getVpnlMain().removeFromParent();
						pp.getPopup().removeFromParent();

					}
				});
				todoview.getBtnSave().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						saveToDo(todoview);
					}

				});
			}

			private void saveToDo(ToDoView todoview) {
				final ToDo todo = new ToDo();
				todo.setTask(todoview.getTxtAreaTask().getText());
				todo.setDescription(todoview.getTxtBoxDescription().getText());

				Employee assignedTo = new Employee();
				assignedTo.setEmployeeId(Integer.parseInt(todoview.getListBoxAssignedTo().getSelectedValue()));
				JobCreation job = new JobCreation();
				job.setJobCreationId(Integer.parseInt(todoview.getListBoxJobs().getSelectedValue()));

				todo.setJob(job);

				todo.setAssignedTo(assignedTo);
				todo.setRead(false);

				// todo.setDueDate(dueDate.getDatePicker().getValue());
				todo.setDueDate(todoview.getDueDate().getValue());

				rpcService.savetoDo(todo, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("error in rpc savetodo" + caught.getLocalizedMessage());

					}

					@Override
					public void onSuccess(String result) {
						new DisplayAlert(result);
						fetchAssignedFromToDoReLoad();
						pp.getVpnlMain().removeFromParent();
						pp.getPopup().removeFromParent();
					}
				});
			}
		});

		// con.add(p, new VerticalLayoutData(1, 1));

		// panel = new ContentPanel();
		// panel.setHeight(230);
		// panel.setWidth(850);
		// panel.setHeadingText("ToDoRaise");
		// panel.add(con);
		// return panel;
		grid.setHeight("220px");
		con.add(grid);
		return con;
	}

	public void fetchAssignedFromToDoReLoad() {
		if (popUpToDoRaiserFinalView != null && popUpToDoRaiserFinalView.getVpnlMain() != null) {
			popUpToDoRaiserFinalView.getVpnlMain().removeFromParent();
			popUpToDoRaiserFinalView.getPopup().removeFromParent();
		}
		rpcService.fetchAssignedFromToDos(new AsyncCallback<ArrayList<ToDo>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("fetchToDoReload in ToDoRaiserPortal");
			}

			@Override
			public void onSuccess(ArrayList<ToDo> result) {
				// Window.alert("success");
				updatedView(result);
			}
		});
	}

	private void updatedView(ArrayList<ToDo> toDosUpdated) {
		store.clear();
		toDos.clear();
		con.clear();
		setData(toDosUpdated);
		add(createGridFieldWork());
	}
}