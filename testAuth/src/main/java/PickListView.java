import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class PickListView {

    private DualListModel<String> cities;

    @PostConstruct
    public void init() {
        //Cities
        List<String> citiesSource = new ArrayList<>();
        List<String> citiesTarget = new ArrayList<>();

        citiesSource.add("San Francisco");
        citiesSource.add("London");
        citiesSource.add("Paris");
        citiesSource.add("Istanbul");
        citiesSource.add("Berlin");
        citiesSource.add("Barcelona");
        citiesSource.add("Rome");

        cities = new DualListModel<>(citiesSource, citiesTarget);

    }

    public DualListModel<String> getCities() {
        return cities;
    }

    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();

        System.out.println("onTransfer");
    }

    public void onSelect(SelectEvent<String> event) {
        System.out.println("onSelect");
    }

    public void onUnselect(UnselectEvent<String> event) {
        System.out.println("onUnselect");
    }

    public void onReorder() {
        System.out.println("onReorder");
    }
}