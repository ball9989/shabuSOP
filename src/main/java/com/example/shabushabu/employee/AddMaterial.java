package com.example.shabushabu.employee;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class AddMaterial extends FormLayout {
    VerticalLayout mainLayout = new VerticalLayout();
    FormLayout buttonLayout = new FormLayout();
//    H5 materialName = new H5("ชื่อวัตถุดิบ:");
    TextField nameField = new TextField();
//    H5 materialDetail = new H5("รายละเอียด");
    TextField detailField = new TextField();
//    H5 materialNumber = new H5("จำนวนทั้งหมด");
    NumberField numberField = new NumberField();
//    H5 addMaterialNumber = new H5("เพิ่มจำนวนวัตถุดิบ");
    Button confirmBtn = new Button("ยืนยัน");
    Button cancelBtn = new Button("ยกเลิก");
    FormLayout addMaterialLayout = new FormLayout();
    NumberField inputMaterialNumber = new NumberField();

    public AddMaterial() {
        addMaterialLayout.add(new H5("จำนวน"), inputMaterialNumber, new H5("กิโล"));
        addMaterialLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("1px", 1));

        nameField.setLabel("ชื่อวัตถุดิบ");

        confirmBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        cancelBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(cancelBtn, confirmBtn);
        buttonLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("1px", 2));
        mainLayout.add(addMaterialLayout, buttonLayout);

        this.add(mainLayout);
    }
}
