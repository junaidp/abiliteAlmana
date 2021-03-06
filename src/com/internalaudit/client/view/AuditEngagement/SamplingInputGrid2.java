package com.internalaudit.client.view.AuditEngagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.internalaudit.client.InternalAuditService;
import com.internalaudit.client.InternalAuditServiceAsync;
import com.internalaudit.client.view.DisplayAlert;
import com.internalaudit.client.view.LoadingPopup;
import com.internalaudit.shared.AuditWork;
import com.internalaudit.shared.InternalAuditConstants;
import com.internalaudit.shared.SamplingExcelSheetEntity;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnHeader;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.LiveGridView;
import com.sencha.gxt.widget.core.client.grid.LiveToolItem;
import com.sencha.gxt.widget.core.client.grid.filters.BooleanFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class SamplingInputGrid2 extends VerticalLayoutContainer {

	InternalAuditServiceAsync rpcService = GWT.create(InternalAuditService.class);
	
	/*protected GridFilters<T> gridFilters;
    protected BooleanFilter<T> filterText = null;
    protected List<FilterConfig> filters = null;
    protected ArrayList<T> list;*/
	
	protected static final int MIN_HEIGHT = 1;
	protected static final int MIN_WIDTH = 1280;
	protected static final int PREFERRED_HEIGHT = 1;
	protected static final int PREFERRED_WIDTH = 1;
	ArrayList<SamplingExcelSheetEntity> exportList ;
	private ContentPanel panel;
	private Button btnSubmit = new Button("Submit");
	private Button btnExportExcel = new Button("Export To Excel");
	private Button btnExportPDF = new Button("Export To PDF");
	// private static final OutstandingCoachingProperties properties =
	// GWT.create(OutstandingCoachingProperties.class);
	private static final SamplingInputGrid2.InputSheetProperties properties = GWT
			.create(SamplingInputGrid2.InputSheetProperties.class);
	private VerticalLayoutContainer verticalLayoutContainer;
	ListStore<SamplingExcelSheetEntity> store;
	private LiveGridView<SamplingExcelSheetEntity> gridView;
	private PagingLoader<FilterPagingLoadConfig, PagingLoadResult<SamplingExcelSheetEntity>> pagingLoader;
	private ArrayList<SamplingExcelSheetEntity> listSamplingSheet;
	private Grid<SamplingExcelSheetEntity> grid ;
	public SamplingInputGrid2(ArrayList<SamplingExcelSheetEntity> result, TextBox lblPopulationData,
			TextBox lblSamplingSizeData, ListBox listBoxSamplingMethod, Integer auditStepId, Anchor lblSavedAuditReport, Anchor anchorExcelTemplate) {

		setData(result);
		add(createGridFieldWork(lblPopulationData, lblSamplingSizeData, listBoxSamplingMethod));
		clickHandlers(lblPopulationData, lblSamplingSizeData, listBoxSamplingMethod, auditStepId, lblSavedAuditReport, anchorExcelTemplate);
	}


	public interface InputSheetProperties extends PropertyAccess<SamplingExcelSheetEntity> {

		@Path("id")
		ModelKeyProvider<SamplingExcelSheetEntity> key();
		
		ValueProvider<SamplingExcelSheetEntity, Integer> id();

		ValueProvider<SamplingExcelSheetEntity, String> category();
		
		ValueProvider<SamplingExcelSheetEntity, String> docNo();

		ValueProvider<SamplingExcelSheetEntity, String> date();

		ValueProvider<SamplingExcelSheetEntity, String> itemCode();

		ValueProvider<SamplingExcelSheetEntity, String> description();

		ValueProvider<SamplingExcelSheetEntity, String> quantity();

		ValueProvider<SamplingExcelSheetEntity, String> ucost();

		ValueProvider<SamplingExcelSheetEntity, String> transCost();

		ValueProvider<SamplingExcelSheetEntity, String> code();
		
		ValueProvider<SamplingExcelSheetEntity, String> name();


	}

	private void setData(ArrayList<SamplingExcelSheetEntity> result) {
		listSamplingSheet = new ArrayList<SamplingExcelSheetEntity>();
		listSamplingSheet =result;
		//store.addAll(listSamplingSheet);
	//	store.clear();
	}

	public VerticalLayoutContainer createGridFieldWork(TextBox lblPopulationData, TextBox lblSamplingSizeData,
			ListBox listBoxSamplingMethod) {

		
		 RpcProxy<PagingLoadConfig, PagingLoadResult<SamplingExcelSheetEntity>> rpcProxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<SamplingExcelSheetEntity>>() {
		        @Override
		        public void load(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<SamplingExcelSheetEntity>> callback) {
		        getDatas(loadConfig, callback);
		        }
		      };
		      
		       store = new ListStore<SamplingExcelSheetEntity>(new ModelKeyProvider<SamplingExcelSheetEntity>() {
		          @Override
		          public String getKey(SamplingExcelSheetEntity item) {
		            return "" + item.getId();
		          }
		        });
		      
		      final PagingLoader<PagingLoadConfig, PagingLoadResult<SamplingExcelSheetEntity>> gridLoader = new PagingLoader<PagingLoadConfig, PagingLoadResult<SamplingExcelSheetEntity>>(rpcProxy);
		      gridLoader.setRemoteSort(true);
		      
		IdentityValueProvider<SamplingExcelSheetEntity> identity = new IdentityValueProvider<SamplingExcelSheetEntity>();
		
		ColumnConfig<SamplingExcelSheetEntity, Integer> samplingSheetId = new ColumnConfig<SamplingExcelSheetEntity, Integer>(
				properties.id(), 90, "Id");
		ColumnConfig<SamplingExcelSheetEntity, String> category = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.category(), 100, "Category");
		ColumnConfig<SamplingExcelSheetEntity, String> docNo = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.docNo(), 150, "Doc#");
		ColumnConfig<SamplingExcelSheetEntity, String> date = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.date(), 140, "Date");
		ColumnConfig<SamplingExcelSheetEntity, String> itemCode = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.itemCode(), 150, "item Code");		
		ColumnConfig<SamplingExcelSheetEntity, String> description = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.description(), 240, "Item Description");
		ColumnConfig<SamplingExcelSheetEntity, String> quantity = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.quantity(), 150, "Quantity");
		ColumnConfig<SamplingExcelSheetEntity, String> uCost = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.ucost(), 150, "U.Cost");
		ColumnConfig<SamplingExcelSheetEntity, String> transCost = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.transCost(), 150, "Trans.Cost");
		ColumnConfig<SamplingExcelSheetEntity, String> code = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.code(), 140, "Code");
		ColumnConfig<SamplingExcelSheetEntity, String> name = new ColumnConfig<SamplingExcelSheetEntity, String>(
				properties.name(), 140, "Name");
		

		// Cell cellDueDate = new
		// DateCell(DateTimeFormat.getFormat("MM/dd/yy"));
		// outstandingOverDue.setCell(cellDueDate);
		btnExportExcel.setVisible(false);
		btnExportPDF.setVisible(false);
		btnExportExcel.setWidth("105px");
		btnExportPDF.setWidth("105px");
		//btnExportPDF.getElement().getStyle().setMarginLeft(40, Unit.PX);

		List<ColumnConfig<SamplingExcelSheetEntity, ?>> columns = new ArrayList<ColumnConfig<SamplingExcelSheetEntity, ?>>();
		columns.add(samplingSheetId);
		columns.add(category);
		columns.add(docNo);
		columns.add(date);
		columns.add(itemCode);
		columns.add(description);
		columns.add(quantity);
		columns.add(uCost);
		columns.add(transCost);
		columns.add(code);
		columns.add(name);

		  final LiveGridView<SamplingExcelSheetEntity> liveGridView = new LiveGridView<SamplingExcelSheetEntity>();
	      liveGridView.setAutoExpandColumn(samplingSheetId);
		
		ColumnModel<SamplingExcelSheetEntity> cm = new ColumnModel<SamplingExcelSheetEntity>(columns);

	      Grid<SamplingExcelSheetEntity> gridView = new Grid<SamplingExcelSheetEntity>(store, cm) {
	          @Override
	          protected void onAfterFirstAttach() {
	            super.onAfterFirstAttach();
	            // After the Grid has been attached to DOM load the data
	            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	              @Override
	              public void execute() {
	                // Important: set the loader limit to the live grid view cache size!!!!
	                gridLoader.load(0, liveGridView.getCacheSize());
	              }
	            });
	          }
	        };
	        gridView.setLoadMask(true);
	        gridView.setLoader(gridLoader);
	        gridView.setView(liveGridView);

	        ToolBar toolBar = new ToolBar();
	        toolBar.setBorders(false);
	        toolBar.add(new LiveToolItem(gridView));
	        toolBar.getElement().getStyle().setMargin(10, Unit.PX);


		verticalLayoutContainer = new VerticalLayoutContainer();
		
		HorizontalPanel panelExportButton = new HorizontalPanel();
		panelExportButton.add(btnExportExcel);
		panelExportButton.add(btnExportPDF);
		panelExportButton.addStyleName("w3-right");
		
		
		btnSubmit.addStyleName("w3-right");
	  verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, 30));
	  verticalLayoutContainer.add(gridView, new VerticalLayoutData(1, 500));
	  verticalLayoutContainer.add(btnSubmit);
	  verticalLayoutContainer.add(panelExportButton);
	  verticalLayoutContainer.setSize("1190px", "550px");
	      
	  return verticalLayoutContainer;
		
	}

	private void getDatas(PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<SamplingExcelSheetEntity>> callback)
	{
		try
		{
			if (listSamplingSheet == null)
			{
				callback.onFailure(new Exception("empty Sample"));
				return;
			}
			final int offset = loadConfig.getOffset();
			int limit = loadConfig.getLimit();
			final List<SamplingExcelSheetEntity> datas = new ArrayList<SamplingExcelSheetEntity>();
			int end = offset + limit;
			if(end > listSamplingSheet.size()) end = listSamplingSheet.size();
			datas.clear();

			for (int i = offset; i < end; i++)
			{
				datas.add((SamplingExcelSheetEntity) listSamplingSheet.get( i ));
			}

			PagingLoadResult<SamplingExcelSheetEntity> result = new PagingLoadResult<SamplingExcelSheetEntity>()
			{

				private static final long serialVersionUID = 1L;

				@Override
				public List<SamplingExcelSheetEntity> getData()
				{

					return datas;
				}

				@Override
				public void setTotalLength(int totalLength)
				{

				}

				@Override
				public void setOffset(int offset)
				{
				}

				@Override
				public int getTotalLength()
				{
					return listSamplingSheet.size();
				}

				@Override
				public int getOffset()
				{
					return offset;
				}
			};
			callback.onSuccess(result);
		}
		catch (

				Exception ex)
		{
			System.out.println("err");
		}
	}

	
	
	private void clickHandlers(final TextBox lblPopulationData, final TextBox lblSamplingSizeData,
			final ListBox listBoxSamplingMethod,final Integer auditStepId, final Anchor lblSavedAuditReport, final Anchor anchorExcelTemplate) {

		btnSubmit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				generateSamplingOutpot(lblPopulationData, lblSamplingSizeData, listBoxSamplingMethod,auditStepId,lblSavedAuditReport,anchorExcelTemplate);
			}

			
		});
		
		btnExportExcel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				String reportFormat = InternalAuditConstants.EXCEL;
				generateReport(listBoxSamplingMethod, reportFormat, auditStepId, lblSavedAuditReport,anchorExcelTemplate);
				
			}
		});
		
		btnExportPDF.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				String reportFormat = InternalAuditConstants.PDF;
				generateReport(listBoxSamplingMethod, reportFormat, auditStepId ,lblSavedAuditReport,anchorExcelTemplate);
				
			}
		});
	}
	private void generateReport(final ListBox listBoxSamplingMethod, String reportFormat,final Integer auditStepId,final Anchor lblSavedAuditReport,final Anchor anchorExcelTemplate) {
		final LoadingPopup loadingpopup = new LoadingPopup();
		loadingpopup.display();
		rpcService.exportSamplingAuditStep(listBoxSamplingMethod.getSelectedItemText(), reportFormat, exportList,auditStepId, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable arg0) {
				Window.alert("Error in exportSamplingAuditStep + ");
				
			}

			@Override
			public void onSuccess(String report) {
				Window.alert(report);
				loadingpopup.remove();
				if (report.contains(InternalAuditConstants.PDF)) {
					Window.open("SamplingAuditStep/reportSamplingAuditPDF.pdf", "_blank", "");

				} else {

					Window.open("SamplingAuditStep/reportSamplingAudit.xls", "_blank", "");
				}
				fetchSavedSamplingPDF(auditStepId,lblSavedAuditReport,anchorExcelTemplate);
				
			}
		});
	}
	
	private void generateSamplingOutpot(final TextBox lblPopulationData, final TextBox lblSamplingSizeData,
			final ListBox listBoxSamplingMethod,final Integer auditStepId,final Anchor lblSavedAuditReport,final Anchor anchorExcelTemplate) {
		rpcService.generateSamplingOutput(lblPopulationData.getText(), lblSamplingSizeData.getText(),
				listBoxSamplingMethod.getSelectedItemText(), listSamplingSheet, auditStepId,
				new AsyncCallback<ArrayList<SamplingExcelSheetEntity>>() {

					@Override
					public void onSuccess(ArrayList<SamplingExcelSheetEntity> samplingList) {
						new DisplayAlert("File Readed");
						exportList = samplingList;
						verticalLayoutContainer.clear();
						verticalLayoutContainer.hide();
						setData(samplingList);
						remove(createGridFieldWork(lblPopulationData, lblSamplingSizeData, listBoxSamplingMethod));
						add(createGridFieldWork(lblPopulationData, lblSamplingSizeData, listBoxSamplingMethod));
						btnSubmit.setVisible(false);
						btnExportExcel.setVisible(true);
						btnExportPDF.setVisible(true);
						
						
						fetchSavedSamplingPDF(auditStepId,lblSavedAuditReport,anchorExcelTemplate);
							
					}

					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("Fail");
					}
				});
	}
	
	private void fetchSavedSamplingPDF(Integer auditStepId,final Anchor lblSavedAuditReport,final Anchor anchorExcelTemplate) {
		rpcService.fetchSavedSamplingReport(InternalAuditConstants.SAMPLINGAUDITSTEPFOLDEER, auditStepId+"", new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable arg0) {
			//	Window.alert("Failed Fetching file");
				
			}

			@Override
			public void onSuccess(String file) {
				
				lblSavedAuditReport.setVisible(true);
				lblSavedAuditReport.setText(file);
				anchorExcelTemplate.setVisible(false);
				
			}
		});
	}

}

