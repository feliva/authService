import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.schedule.ScheduleEntryMoveEvent;
import org.primefaces.event.schedule.ScheduleEntryResizeEvent;
import org.primefaces.event.schedule.ScheduleRangeEvent;
import org.primefaces.model.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Named
@ViewScoped
public class ScheduleJava8View implements Serializable {


    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();

        addEvents2EventModel(LocalDateTime.now());
        addEvents2EventModel(LocalDateTime.now().minusMonths(6));

        lazyEventModel = new LazyScheduleModel() {

            @Override
            public void loadEvents(LocalDateTime start, LocalDateTime end) {
                for (int i = 1; i <= 5; i++) {
                    LocalDateTime random = getRandomDateTime(start);
                    addEvent(DefaultScheduleEvent.builder()
                            .title("Lazy Event " + i)
                            .startDate(random)
                            .endDate(random.plusHours(3))
                            .build());
                }
            }
        };

//        extenderExamples = extenderService.createExtenderExamples();
    }

    private void addEvents2EventModel(LocalDateTime referenceDate) {
        DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                .title("Champions League Match")
                .startDate(previousDay8Pm(referenceDate))
                .endDate(previousDay11Pm(referenceDate))
                .description("Team A vs. Team B")
                .url("https://www.uefa.com/uefachampionsleague/")
                .borderColor("orange")
                .build();
        eventModel.addEvent(event);

        event = DefaultScheduleEvent.builder()
                .startDate(referenceDate.minusDays(6))
                .endDate(referenceDate.minusDays(3))
                .overlapAllowed(true)
                .editable(false)
                .resizable(false)
                .display(ScheduleDisplayMode.BACKGROUND)
                .backgroundColor("lightgreen")
                .build();
        eventModel.addEvent(event);

        event = DefaultScheduleEvent.builder()
                .title("Birthday Party")
                .startDate(today1Pm(referenceDate))
                .endDate(today6Pm(referenceDate))
                .description("Aragon")
                .overlapAllowed(true)
                .borderColor("#CB4335")
                .id("darlan")
                .build();
        eventModel.addEvent(event);

        event = DefaultScheduleEvent.builder()
                .title("Breakfast at Tiffanys (always resizable)")
                .startDate(nextDay9Am(referenceDate))
                .endDate(nextDay11Am(referenceDate))
                .description("all you can eat")
                .overlapAllowed(true)
                .resizable(true)
                .borderColor("#27AE60")
                .build();
        eventModel.addEvent(event);

        event = DefaultScheduleEvent.builder()
                .title("Plant the new garden stuff (always draggable)")
                .startDate(theDayAfter3Pm(referenceDate))
                .endDate(fourDaysLater3pm(referenceDate))
                .description("Trees, flowers, ...")
                .draggable(true)
                .borderColor("#27AE60")
                .build();
        eventModel.addEvent(event);

        DefaultScheduleEvent<?> scheduleEventAllDay = DefaultScheduleEvent.builder()
                .title("Holidays (AllDay)")
                .startDate(sevenDaysLater0am(referenceDate))
                .endDate(eightDaysLater0am(referenceDate))
                .description("sleep as long as you want")
                .borderColor("#27AE60")
                .allDay(true)
                .build();
        eventModel.addEvent(scheduleEventAllDay);
    }

    public LocalDateTime getRandomDateTime(LocalDateTime base) {
        LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
        return dateTime.plusDays(((int) (Math.random() * 30)));
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    private LocalDateTime previousDay8Pm(LocalDateTime referenceDate) {
        return referenceDate.minusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime previousDay11Pm(LocalDateTime referenceDate) {
        return referenceDate.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today1Pm(LocalDateTime referenceDate) {
        return referenceDate.withHour(13).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime theDayAfter3Pm(LocalDateTime referenceDate) {
        return referenceDate.plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today6Pm(LocalDateTime referenceDate) {
        return referenceDate.withHour(18).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay9Am(LocalDateTime referenceDate) {
        return referenceDate.plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay11Am(LocalDateTime referenceDate) {
        return referenceDate.plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime fourDaysLater3pm(LocalDateTime referenceDate) {
        return referenceDate.plusDays(4).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime sevenDaysLater0am(LocalDateTime referenceDate) {
        return referenceDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime eightDaysLater0am(LocalDateTime referenceDate) {
        return referenceDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDate getInitialDate() {
        return LocalDate.now().plusDays(1);
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public void addEvent() {
        if (event.isAllDay()) {
            // see https://github.com/primefaces/primefaces/issues/1164
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            eventModel.addEvent(event);
        }
        else {
            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent<>();
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
    }

    public void onViewChange(SelectEvent<String> selectEvent) {
        String view = selectEvent.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "View Changed", "View:" + view);
        addMessage(message);
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .build();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved",
                "Delta:" + event.getDeltaAsDuration());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized",
                "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

        addMessage(message);
    }

    public void onRangeSelect(ScheduleRangeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Range selected",
                "Start-Date:" + event.getStartDate() + ", End-Date: " + event.getEndDate());

        addMessage(message);
    }

    public void onEventDelete() {
        String eventId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventId");
        if (event != null) {
            ScheduleEvent<?> event = eventModel.getEvent(eventId);
            eventModel.deleteEvent(event);
        }
    }


    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


}