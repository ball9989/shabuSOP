package com.example.shabushabu.manager;

import com.example.shabushabu.pojo.Menu;
import com.example.shabushabu.pojo.Order;
import com.example.shabushabu.pojo.Orders;
import com.example.shabushabu.pojo.ServeOrder;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

public class OrderGridView extends VerticalLayout {
    Orders orders = new Orders();
    private int index = 0;
    private String orderId;
    private Text orderIdText = new Text("เลขออร์เดอร์");
    private Text tableNoText = new Text("");
    public OrderGridView(){
        getOrders();
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Create new employee");
        dialog.setDraggable(true);
        VerticalLayout dialogLayout = viewDialogLayout(dialog);
        dialog.add(dialogLayout);
        H2 title = new H2("รายการออร์เดอร์");
        Grid<Order> grid = new Grid<>(Order.class,false);
        grid.setItems(orders.model);
        grid.addColumn(Order::get_id).setHeader("ID");
        grid.addColumn(Order::getTableNo).setHeader("Table");
        grid.addColumn(Order::getTotalPrice).setHeader("Total-price");
        grid.addColumn(Order::getStatus).setHeader("Status");
        grid.addComponentColumn(item ->{
            Button view = new Button("view");
            view.addClickListener(e->{
                this.index = orders.model.indexOf(item);
                this.orderId = item.get_id();
                this.orderIdText.setText("เลขออร์เดอร์ : "+item.get_id());
                this.tableNoText.setText("เลขโต๊ะ : "+item.getTableNo()+" ");
                dialog.open();
            });
            return view;
        });
        add(title,grid);
        setWidth("100%");
        this.setVisible(false);
    }

    private VerticalLayout viewDialogLayout(Dialog dialog) {
        H2 headline = new H2("ดูรายละเอียด Order");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        Grid<ServeOrder> grid = new Grid<>(ServeOrder.class,false);
        grid.setItems(orders.model.get(index).getOrder());
        grid.addColumn(ServeOrder::getName).setHeader("ชื่อเมนู");
        grid.addColumn(ServeOrder::getCount).setHeader("จำนวนที่สั่ง");
        grid.addColumn(ServeOrder::getPrice).setHeader("ราคาต่อชิ้น");
        grid.addColumn(ServeOrder::getTotalPrice).setHeader("ราคารวม");


        VerticalLayout fieldLayout = new VerticalLayout();
        fieldLayout.add(tableNoText,orderIdText);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button saveButton = new Button("ตกลง");
        saveButton.addClickListener(e ->{
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,grid,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "500px").set("max-width", "100%");

        return dialogLayout;
    }


    public void getOrders(){
        Orders out = WebClient.create().get()
                .uri("http://localhost:8080/orders")
                .retrieve()
                .bodyToMono(Orders.class)
                .block();
        System.out.println("out " +out);
        for (int i = 0; i < out.model.size(); i++) {
            if(out.model.get(i).getStatus().equals("success")){
                orders.model.add(out.model.get(i));
            }
        }
    }
}
