package br.unirio.jdbpn.narrativas.util;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.unirio.jdbpn.narrativas.model.Personagem;

@FacesConverter(value = "personagemPickListConverter")
public class PersonagemPickListConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Object ret = null;
		if (arg1 instanceof PickList) {
			Object dualList = ((PickList) arg1).getValue();
			@SuppressWarnings("unchecked")
			DualListModel<Object> dl = (DualListModel<Object>) dualList;
			for (Iterator<Object> iterator = dl.getSource().iterator(); iterator.hasNext();) {
				Object o = iterator.next();
				String id = (new StringBuilder()).append(((Personagem) o).getId()).toString();
				if (arg2.equals(id)) {
					ret = o;
					break;
				}
			}

			if (ret == null) {
				for (Iterator<Object> iterator1 = dl.getTarget().iterator(); iterator1.hasNext();) {
					Object o = iterator1.next();
					String id = (new StringBuilder()).append(((Personagem) o).getId()).toString();
					if (arg2.equals(id)) {
						ret = o;
						break;
					}
				}

			}
		}
		return ret;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String str = "";
		if (arg2 instanceof Personagem)
			str = String.valueOf(((Personagem) arg2).getId());
		return str;
	}
}
