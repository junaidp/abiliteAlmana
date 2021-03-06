package com.internalaudit.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.upload.AuditWorkProgramUpload;
import com.internalaudit.shared.Employee;
import com.internalaudit.shared.InternalAuditConstants;
import com.internalaudit.shared.JobCreation;
import com.sencha.gxt.widget.core.client.form.DateField;

public class ToDoView extends Composite {

	@UiField
	TextArea txtBoxDescription;
	@UiField
	ListBox listBoxAssignedTo;
	@UiField
	DateField dueDate;
	@UiField
	Button btnSave;
	@UiField
	ListBox listBoxJobs;
	@UiField
	Button btnCancel;
	@UiField
	TextArea txtAreaTask;
	@UiField
	VerticalPanel panelAttachment;
	private InternalAuditServiceAsync rpcService;

	private static ToDoViewUiBinder uiBinder = GWT.create(ToDoViewUiBinder.class);

	interface ToDoViewUiBinder extends UiBinder<Widget, ToDoView> {

	}

	public ToDoView() {
		initWidget(uiBinder.createAndBindUi(this));
		// todo.setRes[]
		rpcService = GWT.create(InternalAuditService.class);
		fetchEmployees();
		fetchJobs();
		setHandlers();
		String toDoId = "check";
		String mainFolder = "ToDoUploads";
		listBoxAssignedTo.addItem("--Select Person--");
		listBoxJobs.addItem("--Select Job--");
		txtBoxDescription.getElement().setPropertyString("placeholder", "Enter text here");
		AuditWorkProgramUpload toDoAttachmentUploqad = new AuditWorkProgramUpload(toDoId, mainFolder);
		panelAttachment.add(toDoAttachmentUploqad);
		toDoAttachmentUploqad.getPanelFileDetail().setWidth("350px");
		// dueDate.getElement().setPropertyString("placeholder", " dd/mm/yyyy");
		// DateBox.DefaultFormat(DateTimeFormat.getFormat("dd MMMM , yyyy")));
		txtAreaTask.getElement().setPropertyString("placeholder", "Enter text here");
	}

	public void deleteUnSavedAttachments() {
		rpcService.deleteUnsavedAttachemnts(InternalAuditConstants.PATHTODOUPLOADS, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail deleteUnsavedAttachments" + caught.getCause());

			}

			@Override
			public void onSuccess(String result) {
				System.out.println(result);

			}
		});
	}

	private void setHandlers() {
		btnCancel.getElement().getStyle().setMarginTop(3, Unit.PX);
		btnCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				deleteUnSavedAttachments();

			}

		});
		btnSave.getElement().getStyle().setMarginLeft(185, Unit.PX);
		btnSave.getElement().getStyle().setMarginTop(3, Unit.PX);
		/*
		 * btnSave.addClickHandler(new ClickHandler() {
		 * 
		 * @Override public void onClick(ClickEvent event) {
		 * 
		 * saveToDo(); }
		 * 
		 * }); }
		 * 
		 * private void saveToDo() { final ToDo todo = new ToDo();
		 * todo.setDescription(txtBoxDescription.getText());
		 * 
		 * Employee assignedTo = new Employee();
		 * assignedTo.setEmployeeId(Integer.parseInt(listBoxAssignedTo.
		 * getSelectedValue())); JobCreation job = new JobCreation();
		 * job.setJobCreationId(Integer.parseInt(listBoxJobs.getSelectedValue())
		 * );
		 * 
		 * todo.setJob(job);
		 * 
		 * todo.setAssignedTo(assignedTo); todo.setRead(false);
		 * 
		 * // todo.setDueDate(dueDate.getDatePicker().getValue());
		 * todo.setDueDate(dueDate.getValue());
		 * 
		 * rpcService.savetoDo(todo, new AsyncCallback<String>() {
		 * 
		 * @Override public void onFailure(Throwable caught) {
		 * Window.alert("error in rpc savetodo" + caught.getLocalizedMessage());
		 * 
		 * }
		 * 
		 * @Override public void onSuccess(String result) { new
		 * DisplayAlert(result); rpcService.fetchToDoReLoad(new
		 * AsyncCallback<ArrayList<ToDo>>() {
		 * 
		 * @Override public void onFailure(Throwable caught) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onSuccess(ArrayList<ToDo> result) {
		 * Window.alert("success");
		 * fetchUpdatedToDos(todo.getAssignedTo().getEmployeeId()); } });
		 * 
		 * } });
		 */
	}

	/*
	 * private void fetchUpdatedToDos(final int employeeID) {
	 * rpcService.fetchUpdatedRaisedToDo(employeeID, new
	 * AsyncCallback<ArrayList<ToDo>>() {
	 * 
	 * @Override public void onFailure(Throwable caught) { // TODO
	 * Auto-generated method stub Window.alert("failed Updated TODOS"); }
	 * 
	 * @Override public void onSuccess(ArrayList<ToDo> toDosUpdated) { // TODO
	 * Auto-generated method stub Window.alert("success Updated TODOS"); new
	 * ToDoRaiserPortal(toDosUpdated); } }); }
	 */
	private void fetchEmployees() {
		rpcService.fetchEmployees(new AsyncCallback<ArrayList<Employee>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail fetch Employees");
			}

			@Override
			public void onSuccess(ArrayList<Employee> result) {

				for (int i = 0; i < result.size(); i++) {

					listBoxAssignedTo.addItem(result.get(i).getEmployeeName(), result.get(i).getEmployeeId() + "");

				}
			}

		});
	}

	private void fetchJobs() {

		rpcService.fetchJobs(new AsyncCallback<ArrayList<JobCreation>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("fail fetch jobs");

			}

			@Override
			public void onSuccess(ArrayList<JobCreation> result) {
				for (int i = 0; i < result.size(); i++) {
					listBoxJobs.addItem(result.get(i).getJobName(), result.get(i).getJobCreationId() + "");
				}
			}
		});

	}

	public TextArea getTxtBoxDescription() {
		return txtBoxDescription;
	}

	public void setTxtBoxDescription(TextArea txtBoxDescription) {
		this.txtBoxDescription = txtBoxDescription;
	}

	public ListBox getListBoxAssignedTo() {
		return listBoxAssignedTo;
	}

	public void setListBoxAssignedTo(ListBox listBoxAssignedTo) {
		this.listBoxAssignedTo = listBoxAssignedTo;
	}

	public DateField getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateField dueDate) {
		this.dueDate = dueDate;
	}

	public ListBox getListBoxJobs() {
		return listBoxJobs;
	}

	public void setListBoxJobs(ListBox listBoxJobs) {
		this.listBoxJobs = listBoxJobs;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public TextArea getTxtAreaTask() {
		return txtAreaTask;
	}

	public void setTxtAreaTask(TextArea txtAreaTask) {
		this.txtAreaTask = txtAreaTask;
	}

}
