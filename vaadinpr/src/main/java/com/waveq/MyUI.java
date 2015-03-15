package com.waveq;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.waveq.view.SimpleLoginMainView;
import com.waveq.view.SimpleLoginView;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.waveq.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        // Create a new instance of the navigator. The navigator will attach
        // itself automatically to this view.
        new Navigator(this, this);

        // The initial log view where the user can login to the application
        getNavigator().addView(SimpleLoginView.NAME, SimpleLoginView.class);//

        // Add the main view of the application
        getNavigator().addView(SimpleLoginMainView.NAME,
                SimpleLoginMainView.class);

        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof SimpleLoginView;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(SimpleLoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeListener.ViewChangeEvent event) {

            }
        });
    }

//    FilesystemContainer docs = new FilesystemContainer(new File("D:\\\\Jape"));
//    Table docList = new Table("Documents", docs);
//    Label docView = new Label("", ContentMode.HTML);
//
//
//    @Override
//    protected void init(VaadinRequest vaadinRequest) {
//        setContent(docList);
//
//        HorizontalSplitPanel split = new HorizontalSplitPanel();
//        setContent(split);
//        split.addComponent(docList);
//        split.addComponent(docView);
//        docList.setSizeFull();
//
//        docList.addValueChangeListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
//                docView.setPropertyDataSource(new TextFileProperty((File) valueChangeEvent.getProperty().getValue()));
//            }
//        });
//        docList.setImmediate(true);
//        docList.setSelectable(true);
////        final VerticalLayout layout = new VerticalLayout();
////        layout.setMargin(true);
////        setContent(layout);
////
////        Button button = new Button("Click Me");
////        button.addClickListener(new Button.ClickListener() {
////            @Override
////            public void buttonClick(ClickEvent event) {
////                layout.addComponent(new Label("Thank yodu for dsadsaclicking"));
////            }
////        });
////        layout.addComponent(button);
//
//    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(widgetset="com.waveq.MyAppWidgetset",ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
