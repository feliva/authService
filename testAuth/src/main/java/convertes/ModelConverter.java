package convertes;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.Model;

import java.util.HashMap;

@Named
@RequestScoped
public class ModelConverter implements Converter<Model> {

    private static HashMap<Integer, Class> classes;

    static {
        classes = new HashMap<>();
//        classes.put(ColunaImpl.class.hashCode(),ColunaImpl.class);
//        classes.put(CardImpl.class.hashCode(),CardImpl.class);
    }

//    @Inject
//    KambanService kambanService;

    @Override
    public Model getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.equals("")){
            return null;
        }

        String[]name = value.split(Model.SEPARATIOR_KEY);

//        Class<?> entity = Class.forName(classes.get(Integer.parseInt(name[0])).);
//        Class generic =  (Class) ((ParameterizedType)entity.getGenericSuperclass()).getActualTypeArguments()[0];

//        Model m = (Model) this.kambanService.get(name[1]);
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Model value) {

        return value.getKeyConverter();
    }
}
