import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.behavior.ClientBehavior;
import jakarta.faces.component.behavior.ClientBehaviorContext;
import jakarta.faces.component.behavior.ClientBehaviorHolder;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;

@FacesComponent(createTag = true, namespace = "feliva.simplex",
		tagName = "myCustom", value = "MyCustom")
public class MyCustom extends UIComponentBase implements ClientBehaviorHolder {

	@Override
	public String getFamily() {
		return "custom";
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {

		ClientBehaviorContext behaviorContext = ClientBehaviorContext.createClientBehaviorContext(context, this,
				"click", getClientId(context), null);

		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", null);
		responseWriter.writeAttribute("id", getClientId(context), "id");
		responseWriter.writeAttribute("name", getClientId(context), "clientId");

		Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
		if (behaviors.containsKey("click")) {
			String click = behaviors.get("click").get(0).getScript(behaviorContext);
			responseWriter.writeAttribute("onclick", click, null);
		}
		responseWriter.write("Click me!");
		responseWriter.endElement("div");
	}

	@Override
	public void decode(FacesContext context) {
		Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
		if (behaviors.isEmpty()) {
			return;
		}

		ExternalContext external = context.getExternalContext();
		Map<String, String> params = external.getRequestParameterMap();
		String behaviorEvent = params.get("javax.faces.behavior.event");

		if (behaviorEvent != null) {
			List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);

			if (behaviors.size() > 0) {
				String behaviorSource = params.get("javax.faces.source");
				String clientId = getClientId(context);
				if (behaviorSource != null && behaviorSource.equals(clientId)) {
					for (ClientBehavior behavior : behaviorsForEvent) {
						behavior.decode(context, this);
					}
				}
			}
		}
	}

	@Override
	public Collection<String> getEventNames() {
		return Arrays.asList("click");
	}

	@Override
	public String getDefaultEventName() {
		return "click";
	}
}