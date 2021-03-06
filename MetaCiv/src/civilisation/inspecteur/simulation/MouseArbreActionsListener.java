package civilisation.inspecteur.simulation;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class MouseArbreActionsListener implements MouseListener{

	PanelArbreActions p;
	
	public MouseArbreActionsListener(PanelArbreActions p)
	{
		this.p = p;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    if(SwingUtilities.isRightMouseButton(e)){
	    	if(p.getArbreActions().getPathForLocation(e.getX(), e.getY()) != null)
	    	{
	    		p.setActionActive(((NodeArbreActions)p.getArbreActions().getPathForLocation(e.getX(), e.getY()).getLastPathComponent()).getAction());
	    		p.setNodeActionActive((NodeArbreActions)p.getArbreActions().getPathForLocation(e.getX(), e.getY()).getLastPathComponent());
				p.afficherPopup(e,((NodeArbreActions)p.getArbreActions().getPathForLocation(e.getX(), e.getY()).getLastPathComponent()).getAction());	    		
	    	}
	    }	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	/*	p.setDragAction(((NodeArbreActions)p.getArbreActions().getPathForLocation(e.getX(), e.getY()).getLastPathComponent()).getAction());
		p.setNodeDrag((NodeArbreActions)p.getArbreActions().getPathForLocation(e.getX(), e.getY()).getLastPathComponent());
		*/
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		/*p.setActionActive(((NodeArbreActions)p.getArbreActions().getPathForLocation(e.getX(), e.getY()).getLastPathComponent()).getAction());
		p.setNodeActionActive((NodeArbreActions)p.getArbreActions().getPathForLocation(e.getX(), e.getY()).getLastPathComponent());
		
		if(p.getNodeActionActive()!=p.getNodeDrag()){
			p.drag();
		}*/
		
	}
	


}
