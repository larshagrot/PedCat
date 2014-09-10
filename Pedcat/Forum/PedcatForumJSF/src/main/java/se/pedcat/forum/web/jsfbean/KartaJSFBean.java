/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.pedcat.forum.web.jsfbean;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Overlay;
import org.primefaces.model.map.Polygon;
import org.primefaces.model.map.Polyline;
/**
 *
 * @author laha
 */

@ManagedBean(name="kartaJSFBean")
@ApplicationScoped
public class KartaJSFBean implements java.io.Serializable,MapModel {

   // private List<Marker> markerList = new ArrayList<Marker>();
    private MapModel advancedModel =  new DefaultMapModel();
    private Marker marker;
    
    public KartaJSFBean()
    {
        LatLng coord1 = new LatLng(59.486403,18.084977);
        advancedModel.addOverlay(new Marker(coord1,"Start", "KlartForStart2_small.jpg", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
   LatLng[] points = new LatLng[]
   {
new LatLng(59.486588,18.084269),
new LatLng(59.487373,18.08856),
new LatLng(59.487896,18.089848),
new LatLng(59.488724,18.091307),
new LatLng(59.489508,18.09268),
new LatLng(59.490205,18.093452),
new LatLng(59.491164,18.093624),
new LatLng(59.49173,18.093882),
new LatLng(59.492428,18.094053),
new LatLng(59.49282,18.094311),
new LatLng(59.493735,18.09474),
new LatLng(59.49404,18.094568),
new LatLng(59.494432,18.094139),
new LatLng(59.494432,18.093624),
new LatLng(59.494127,18.092508),
new LatLng(59.493778,18.091993),
new LatLng(59.493473,18.091478),
new LatLng(59.493255,18.091307),
new LatLng(59.493299,18.090277),
new LatLng(59.493604,18.089933),
new LatLng(59.494519,18.089762),
new LatLng(59.49478,18.09062),
new LatLng(59.495042,18.091307),
new LatLng(59.495303,18.092508),
new LatLng(59.495521,18.093967),
new LatLng(59.495478,18.094568),
new LatLng(59.494911,18.094397),
new LatLng(59.494606,18.094397),
new LatLng(59.494083,18.094912),
new LatLng(59.493996,18.095341),
new LatLng(59.493735,18.096886),
new LatLng(59.493735,18.097486),
new LatLng(59.494345,18.097658),
new LatLng(59.494911,18.097744),
new LatLng(59.495303,18.097916),
new LatLng(59.495695,18.098173),
new LatLng(59.496175,18.098516),
new LatLng(59.496828,18.099804),
new LatLng(59.497002,18.09989),
new LatLng(59.497264,18.100834),
new LatLng(59.496697,18.100748),
new LatLng(59.496175,18.100491),
new LatLng(59.49587,18.099804),
new LatLng(59.49539,18.099546),
new LatLng(59.494737,18.099546),
new LatLng(59.494345,18.099203),
new LatLng(59.493691,18.09886),
new LatLng(59.492994,18.098259),
new LatLng(59.492471,18.09783),
new LatLng(59.491905,18.097486),
new LatLng(59.491295,18.096714),
new LatLng(59.490685,18.095856),
new LatLng(59.490336,18.095598),
new LatLng(59.489987,18.094912),
new LatLng(59.489639,18.094826),
new LatLng(59.48929,18.09474),
new LatLng(59.488855,18.094654),
new LatLng(59.488506,18.094654),
new LatLng(59.488027,18.094568),
new LatLng(59.487504,18.094482),
new LatLng(59.487199,18.094225),
new LatLng(59.486806,18.093624),
new LatLng(59.486458,18.093281),
new LatLng(59.48624,18.092508),
new LatLng(59.485935,18.091221),
new LatLng(59.485978,18.088646),
new LatLng(59.486065,18.088131),
new LatLng(59.48624,18.087187),
new LatLng(59.486153,18.086329),
new LatLng(59.48624,18.085642),
new LatLng(59.48624,18.084955),
new LatLng(59.486458,18.084612),
new LatLng(59.486588,18.084269)
   };

   Polyline polyline = new Polyline();

   
   polyline.setStrokeColor("#FF0000");
   polyline.setStrokeWeight(3);
   polyline.setStrokeOpacity(0.4);
   polyline.getPaths().addAll(Arrays.asList(points));
   this.advancedModel.addOverlay(polyline);     
        
        
        
    }
    
    
    @Override
    public void addOverlay(Overlay ovrl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Marker> getMarkers() {
        return this.advancedModel.getMarkers();
    }

    @Override
    public List<Polyline> getPolylines() {
        return this.advancedModel.getPolylines();
    }

    @Override
    public List<Polygon> getPolygons() {
        return this.advancedModel.getPolygons();
    }

    @Override
    public Overlay findOverlay(String string) {
        return this.advancedModel.findOverlay(string);
    }
    
    public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();
	}
	
	public Marker getMarker() {
		return marker;
	}

    
}
