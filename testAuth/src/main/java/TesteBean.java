
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RateEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class TesteBean implements Serializable {

    private String name;
    private String message;

    private Integer rating1 = 4;
//    List<Column<CardImpl>> listColuna = new ArrayList<>();
//
//    @Inject
//    private KambanService kambanService;

    @PostConstruct
    public void init(){
//        listColuna.add(kambanService.add(new ColunaImpl("Um"))
//                .addCard(kambanService.add(new CardImpl("card1")))
//                .addCard(kambanService.add(new CardImpl( "card2")))
//                .addCard(kambanService.add(new CardImpl( "card3")))
//        );
//        listColuna.add(kambanService.add(new ColunaImpl("dows"))
//                .addCard(kambanService.add(new CardImpl(UUID.randomUUID(), "card4")))
//        );
//        listColuna.add(kambanService.add(new ColunaImpl("tres"))
//                .addCard(kambanService.add(new CardImpl(UUID.randomUUID(), "card5")))
//                .addCard(kambanService.add(new CardImpl(UUID.randomUUID(), "card6")))
//        );
    }

    public void onrate(RateEvent<Integer> rateEvent) {
        System.out.println("onRate:" + rateEvent.getRating());
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + rateEvent.getRating());
//        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void oncancel() {
        System.out.println("onClose.");
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
//        FacesContext.getCurrentInstance().addMessage(null, message);
    }

//    public void onEventMove(KambanCardMoveEvent cardMoveEvent){
//        System.out.println("card:" + cardMoveEvent.getCard().getTitle()+" to " + cardMoveEvent.getRaia().getLabel());
//    }

    public Integer getRating1() {
        return rating1;
    }

    public void setRating1(Integer rating1) {
        this.rating1 = rating1;
    }

//    public void addCard(){
//        ((Column)this.listColuna.get(0)).addCard((Card) kambanService.add(new CardImpl(UUID.randomUUID(), "card7")));
//    }

    public void createMessage() {
        message = "Hello, " + name + "!";
        System.out.println("event");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return UUID.randomUUID() + "ççç";
    }

//    public List<Column<CardImpl>> getColunas(){
//
//        return this.listColuna;
//    }

}