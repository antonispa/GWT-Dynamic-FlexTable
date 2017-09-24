package com.fishes.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Fishes implements EntryPoint {

	private ServiceAsync service = GWT.create(Service.class);

	private final VerticalPanel loginPanel = new VerticalPanel();
	private HorizontalPanel tablePanel = new HorizontalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox usernameBox = new TextBox();
	private PasswordTextBox passwordBox = new PasswordTextBox();
	private Label username = new Label("Username");
	private Label password = new Label("Password");
	private Button loginButton = new Button("login");
	private Button insertButton = new Button("insert");
	private FlexTable table = new FlexTable();
	private TextBox fishId = new TextBox();
	private TextBox fishName = new TextBox();
	private TextBox fishPrice = new TextBox();
	private TextBox id;
	private TextBox name;
	private TextBox price;
	private Label successLabel = new Label("");


	// All CallBacks listed here
	
	AsyncCallback<Login> ServiceCallBack = new AsyncCallback<Login>() {

		@Override
		public void onSuccess(Login result) {

			if (result.getLoginCount() == 1) {

				final VerticalPanel userPanel = new VerticalPanel();

				Anchor logout = new Anchor("logout");
				logout.addStyleName("logout");

				logout.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						loginPanel.setVisible(true);
						tablePanel.setVisible(false);
						addPanel.setVisible(false);
						userPanel.setVisible(false);

					}
				});

				Label user = new Label("Hi " + usernameBox.getText());

				userPanel.add(user);
				user.addStyleName("user");
				userPanel.add(logout);
				userPanel.setVisible(true);

				usernameBox.setText("");
				passwordBox.setText("");

				

				loginPanel.setVisible(false);
				tablePanel.setVisible(true);
				addPanel.setVisible(true);
				
				RootPanel.get("user").add(userPanel);
				
			} else if (result.getLoginCount() == 0) {
				Window.alert("Λάθος στοιχεία!");
			}

		}

		@Override
		public void onFailure(Throwable caught) {
			String details = caught.getMessage();

			System.out.println(details);

		}
	};

	AsyncCallback<FishTable> FishTableCallBack = new AsyncCallback<FishTable>() {

		@Override
		public void onFailure(Throwable caught) {
			String details = caught.getMessage();

			System.out.println(details);
		}

		@Override
		public void onSuccess(FishTable result) {

			table.removeAllRows();
			tablePanel.clear();

			table.setText(0, 0, "id");
			table.setText(0, 1, "Fish");
			table.setText(0, 2, "Price");
			table.setCellPadding(6);
			table.getCellFormatter().addStyleName(0, 0, "header1_3");
			table.getCellFormatter().addStyleName(0, 1, "header2");
			table.getCellFormatter().addStyleName(0, 2, "header1_3");

			tablePanel.add(table);

			for (int i = 0; i < result.getIdArray().length; i++) {

				Button updateBtn = new Button("update");
				Button deleteBtn = new Button("delete");

				id = new TextBox();
				id.setText(result.getIdArray()[i]);
				id.addStyleName("fishId");
				id.setReadOnly(true);

				name = new TextBox();
				name.setText(result.getNameArray()[i]);

				price = new TextBox();
				price.setText(result.getPriceArray()[i]);
				price.addStyleName("fishPrice");

				final int row = table.getRowCount();

				table.setWidget(row, 0, id);
				table.setWidget(row, 1, name);
				table.setWidget(row, 2, price);
				table.setWidget(row, 3, updateBtn);
				table.setWidget(row, 4, deleteBtn);

				name.addKeyDownHandler(new KeyDownHandler() {

					@Override
					public void onKeyDown(KeyDownEvent event) {

						if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
							TextBox idBox = (TextBox) table.getWidget(row, 0);
							TextBox nameBox = (TextBox) table.getWidget(row, 1);
							TextBox priceBox = (TextBox) table.getWidget(row, 2);

							UpdateFish(idBox.getText(), nameBox.getText(), priceBox.getText());
						}

					}
				});

				price.addKeyDownHandler(new KeyDownHandler() {

					@Override
					public void onKeyDown(KeyDownEvent event) {

						if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
							TextBox idBox = (TextBox) table.getWidget(row, 0);
							TextBox nameBox = (TextBox) table.getWidget(row, 1);
							TextBox priceBox = (TextBox) table.getWidget(row, 2);

							UpdateFish(idBox.getText(), nameBox.getText(), priceBox.getText());
						}

					}
				});

				updateBtn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						TextBox idBox = (TextBox) table.getWidget(row, 0);
						TextBox nameBox = (TextBox) table.getWidget(row, 1);
						TextBox priceBox = (TextBox) table.getWidget(row, 2);

						UpdateFish(idBox.getText(), nameBox.getText(), priceBox.getText());

					}
				});

				
				deleteBtn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						TextBox idBox = (TextBox) table.getWidget(row, 0);

						DeleteFish(idBox.getText());

					}
				});
			}

		}

	};

	AsyncCallback<InsertFish> insertFishCallBack = new AsyncCallback<InsertFish>() {

		@Override
		public void onSuccess(InsertFish result) {

			FishTable();

			fishId.setText("");
			fishName.setText("");
			fishPrice.setText("");
		}

		@Override
		public void onFailure(Throwable caught) {
			String details = caught.getMessage();

			System.out.println(details);

		}
	};

	AsyncCallback<DeleteFish> deleteFishCallBack = new AsyncCallback<DeleteFish>() {

		@Override
		public void onSuccess(DeleteFish result) {

			successLabel.setText("Successfull deletion");

			// success label will be removed after 5 seconds

			Timer t = new Timer() {
				@Override
				public void run() {
					successLabel.setText("");
				}
			};

			t.schedule(5000);

			FishTable();

		}

		@Override
		public void onFailure(Throwable caught) {
			String details = caught.getMessage();

			System.out.println(details);

		}
	};

	AsyncCallback<UpdateFish> updateFishCallBack = new AsyncCallback<UpdateFish>() {

		@Override
		public void onSuccess(UpdateFish result) {

			successLabel.setText("Successful update");

			// success label will be removed after 5 seconds

			Timer t = new Timer() {
				@Override
				public void run() {
					successLabel.setText("");
				}
			};

			t.schedule(5000);

			FishTable();
		}
		

		@Override
		public void onFailure(Throwable caught) {
			String details = caught.getMessage();

			System.out.println(details);

		}
	};

	public void onModuleLoad() {

		loginPanel.add(username);
		loginPanel.add(usernameBox);
		loginPanel.add(password);
		loginPanel.add(passwordBox);
		loginPanel.add(loginButton);
		loginPanel.addStyleName("loginPanel");
		loginPanel.setSpacing(10);
		
		loginButton.addStyleName("login-button");
		
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
	        public void execute () {
	            usernameBox.setFocus(true);
	        }
		});
		
		tablePanel.add(table);
		addPanel.add(fishId);
		addPanel.add(fishName);
		addPanel.add(fishPrice);
		fishId.addStyleName("fishId");
		fishPrice.addStyleName("fishPrice");

		addPanel.add(insertButton);
		addPanel.addStyleName("addPanel");
		addPanel.setSpacing(13);

		loginPanel.setVisible(true);
		tablePanel.setVisible(false);
		addPanel.setVisible(false);
		
		mainPanel.add(loginPanel);
		mainPanel.add(tablePanel);
		mainPanel.add(successLabel);
		mainPanel.add(addPanel);
		mainPanel.setWidth("100%");
		mainPanel.setHeight("100%");
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		successLabel.addStyleName("success");
		
		RootPanel.get("fishes").add(mainPanel);

		FishTable();
		
		loginButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (usernameBox.getText() == "" || passwordBox.getText() == "") {
					Window.alert("Συμπλήρωσε τα στοιχεία σου");
				}
				Login();
			}
		});

		passwordBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					Login();
				}
			}
		});

		insertButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				InsertFish();
			}
		});

		fishPrice.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					InsertFish();
			}
		});

	}
	
	// end of onModule()

	
	// all methods listed here 
	
	public void Login() {
		if (service == null) {
			service = GWT.create(Service.class);
		}

		service.checkLoginData(usernameBox.getText(), passwordBox.getText(), ServiceCallBack);

	}

	public void FishTable() {
		if (service == null) {
			service = GWT.create(Service.class);
		}

		service.getFishes(FishTableCallBack);
	}

	public void InsertFish() {
		if (service == null) {
			service = GWT.create(Service.class);
		}

		if (fishId.getText() == "" || fishName.getText() == "" || fishPrice.getText() == "") {
			Window.alert("Συμπλήρωσε τα πεδία");
			return;
		}

		service.insertFish(fishId.getText(), fishName.getText(), fishPrice.getText(), insertFishCallBack);
	}

	public void DeleteFish(String id) {
		if (service == null) {
			service = GWT.create(Service.class);
		}

		service.deleteFish(id, deleteFishCallBack);
	}

	public void UpdateFish(String fishId, String fishName, String fishPrice) {
		if (service == null) {
			service = GWT.create(Service.class);
		}

		service.updateFish(fishId, fishName, fishPrice, updateFishCallBack);
	}
}
