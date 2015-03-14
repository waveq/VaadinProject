package com.waveq;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.data.util.TextFileProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

import java.io.File;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.waveq.MyAppWidgetset")
public class MyUI extends UI {

    FilesystemContainer docs = new FilesystemContainer(new File("D:\\\\Jape"));
    Table docList = new Table("Documents", docs);
    Label docView = new Label("", ContentMode.HTML);


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(docList);

        HorizontalSplitPanel split = new HorizontalSplitPanel();
        setContent(split);
        split.addComponent(docList);
        split.addComponent(docView);
        docList.setSizeFull();

        docList.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                docView.setPropertyDataSource(new TextFileProperty((File) valueChangeEvent.getProperty().getValue()));
            }
        });
        docList.setImmediate(true);
        docList.setSelectable(true);
//        final VerticalLayout layout = new VerticalLayout();
//        layout.setMargin(true);
//        setContent(layout);
//
//        Button button = new Button("Click Me");
//        button.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(ClickEvent event) {
//                layout.addComponent(new Label("Thank yodu for dsadsaclicking"));
//            }
//        });
//        layout.addComponent(button);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(widgetset="com.waveq.MyAppWidgetset",ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}