package com.example.application.views.заказы;

import com.example.application.data.entity.Zakazi;
import com.example.application.data.service.ZakaziService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.time.Duration;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("заказы")
@Route(value = "zakazi/:zakaziID?/:action?(edit)")
@RouteAlias(value = "")
public class ZakaziView extends Div implements BeforeEnterObserver {

    private final String ZAKAZI_ID = "zakaziID";
    private final String ZAKAZI_EDIT_ROUTE_TEMPLATE = "zakazi/%s/edit";

    private final Grid<Zakazi> grid = new Grid<>(Zakazi.class, false);

    private TextField name;
    private TextField deadManSurname;
    private TextField uchastok;
    private TextField phone;
    private TextField raboti;
    private DateTimePicker date;
    private TextField stadia;
    private TextField primechanie;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final BeanValidationBinder<Zakazi> binder;

    private Zakazi zakazi;

    private final ZakaziService zakaziService;

    public ZakaziView(ZakaziService zakaziService) {
        this.zakaziService = zakaziService;
        addClassNames("заказы-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("name").setAutoWidth(true).setHeader("Имя заказчика");
        grid.addColumn("deadManSurname").setAutoWidth(true).setHeader("Фамилия умершего");
        grid.addColumn("uchastok").setAutoWidth(true).setHeader("Участок");
        grid.addColumn("phone").setAutoWidth(true).setHeader("Номер телефона");
        grid.addColumn("raboti").setAutoWidth(true).setHeader("Список работ");
        grid.addColumn("date").setAutoWidth(true).setHeader("Дата заказа");
        grid.addColumn("stadia").setAutoWidth(true).setHeader("Стадия выполнения");
        grid.addColumn("primechanie").setAutoWidth(true).setHeader("Примечание");
        grid.setItems(query -> zakaziService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ZAKAZI_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ZakaziView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Zakazi.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.zakazi == null) {
                    this.zakazi = new Zakazi();
                }
                binder.writeBean(this.zakazi);
                zakaziService.update(this.zakazi);
                clearForm();
                refreshGrid();
                Notification.show("Заказ обновлен");
                UI.getCurrent().navigate(ZakaziView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
        delete.addClickListener(e -> {
            if (this.zakazi == null){Notification.show("Заказ не выбран");}
            zakaziService.delete(this.zakazi);
            clearForm();
            refreshGrid();
            Notification.show("заказ удален");

        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> zakaziId = event.getRouteParameters().get(ZAKAZI_ID).map(Long::parseLong);
        if (zakaziId.isPresent()) {
            Optional<Zakazi> zakaziFromBackend = zakaziService.get(zakaziId.get());
            if (zakaziFromBackend.isPresent()) {
                populateForm(zakaziFromBackend.get());
            } else {
                Notification.show(String.format("The requested zakazi was not found, ID = %s", zakaziId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ZakaziView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Имя заказчика");
        deadManSurname = new TextField("Фамилия умершего");
        uchastok = new TextField("Участок");
        phone = new TextField("Номер телефона");
        raboti = new TextField("Список работ");
        date = new DateTimePicker("Дата заказа");
        date.setStep(Duration.ofSeconds(1));
        stadia = new TextField("Стадия выполнения");
        primechanie = new TextField("Примечание");
        formLayout.add(name, deadManSurname, uchastok, phone, raboti, date, stadia, primechanie);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(save, cancel, delete);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Zakazi value) {
        this.zakazi = value;
        binder.readBean(this.zakazi);

    }
}
