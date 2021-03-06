package fr.univrouen.umlreverse.ui.component.clazz.relations;

import fr.univrouen.umlreverse.ui.component.common.elements.IEntityGraphic;
import fr.univrouen.umlreverse.ui.component.common.relation.ARelationGraphic;
import fr.univrouen.umlreverse.ui.component.common.relation.EntityPoint;
import fr.univrouen.umlreverse.ui.component.common.relation.type.RelationTypeEnum;
import fr.univrouen.umlreverse.ui.view.common.IDiagramEditorController;
import javafx.geometry.Point2D;

/**
 * Factorize the code of RelationGraphic. Relation contains 6 points.
 */
public abstract class AClassRelationGraphic extends ARelationGraphic {
    // CONSTRUCTORS
    /**
     * Create a relation component with the given diagram editor controller.
     * @param diagramEditorController
     */
    public AClassRelationGraphic(IDiagramEditorController diagramEditorController) {
        super(diagramEditorController);
    }

// COMMANDS
    @Override
    public final void calculPointRelation() {
        IEntityGraphic src = getController().getGEntitySrc();
        IEntityGraphic dst = getController().getGEntityDst();
        
        Point2D pSrc = src.getCenterPoint();
        Point2D pDst = dst.getCenterPoint();
        double xSrc = pSrc.getX();
        double ySrc = pSrc.getY();
        double xDst = pDst.getX();
        double yDst = pDst.getY();
        double wSrc = src.getWidth() / 2;
        double hSrc = src.getHeight() / 2;
        double wDst = dst.getWidth() / 2;
        double hDst = dst.getHeight() / 2;
    
        Direction d = getDirectionOfDestination();
        switch (d) {
            case NORTH:
                sideSrc.setData(EntityPoint.Side.TOP,
                		new Point2D(xSrc, ySrc - hSrc));
                sideDst.setData(EntityPoint.Side.BOTTOM,
                		new Point2D(xDst, yDst + hDst));
                buildRelationIn3Line();
                break;
            case NORTH_EST:
                sideSrc.setData(EntityPoint.Side.RIGHT,
                		new Point2D(xSrc + wSrc, ySrc));
                sideDst.setData(EntityPoint.Side.BOTTOM,
                		new Point2D(xDst, yDst + hDst));
                buildRelationIn2Line();
                break;
            case EST:
                sideSrc.setData(EntityPoint.Side.RIGHT,
                		new Point2D(xSrc + wSrc, ySrc));
                sideDst.setData(EntityPoint.Side.LEFT,
                		new Point2D(xDst - wDst, yDst));
                buildRelationIn3Line();
                break;    
            case SOUTH_EST:
                sideSrc.setData(EntityPoint.Side.RIGHT,
                		new Point2D(xSrc + wSrc, ySrc));
                sideDst.setData(EntityPoint.Side.TOP,
                		new Point2D(xDst, yDst - hDst));
                buildRelationIn2Line();
                break;
            case SOUTH:
                sideSrc.setData(EntityPoint.Side.BOTTOM,
                		new Point2D(xSrc, ySrc + hSrc));
                sideDst.setData(EntityPoint.Side.TOP,
                		new Point2D(xDst, yDst - hDst));
                buildRelationIn3Line();
                break;
            case SOUTH_WEST:
                sideSrc.setData(EntityPoint.Side.LEFT,
                		new Point2D(xSrc - wSrc, ySrc));
                sideDst.setData(EntityPoint.Side.TOP,
                		new Point2D(xDst, yDst - hDst));
                buildRelationIn2Line();
                break;
            case WEST:
                sideSrc.setData(EntityPoint.Side.LEFT,
                		new Point2D(xSrc - wSrc, ySrc));
                sideDst.setData(EntityPoint.Side.RIGHT,
                		new Point2D(xDst + wDst, yDst));
                buildRelationIn3Line();
                break;
            case NORTH_WEST:
                sideSrc.setData(EntityPoint.Side.LEFT,
                		new Point2D(xSrc - wSrc, ySrc));
                sideDst.setData(EntityPoint.Side.BOTTOM,
                		new Point2D(xDst, yDst + hDst));
                buildRelationIn2Line();
                break;
            case NULL:
                sideSrc.setData(EntityPoint.Side.RIGHT,
                		new Point2D(xSrc + wSrc, ySrc));
                sideDst.setData(EntityPoint.Side.TOP,
                		new Point2D(xDst, yDst - hDst));
                buildRelationToSameEntity();
                break;
            default:
        }  
        nameTextAutoSize();
    }
    
    // TOOLS
    /**
     * Build a relation composed of two lines.
     */
    private void buildRelationIn2Line() {
        Point2D pointSrc = sideSrc.getPoint();
        Point2D pointDst = sideDst.getPoint();
        EntityPoint.Side sideEnumSrc = sideSrc.getSide();
        RelationTypeEnum type = getType();
        double xSrc = pointSrc.getX();
        double ySrc = pointSrc.getY();
        double xDst = pointDst.getX();
        double yDst = pointDst.getY();
        
        clearArrowBodyInGroup();
        arrowBody.clear();
        arrowBody.moveStartPoint(arrowTail.getTailPoint());
        arrowBody.moveEndPoint(arrowHead.getTailPoint());
        arrowTail.setSide(sideEnumSrc);
        arrowHead.setSide(sideDst.getSide());
        drawArrowBodyInGroup();
      
        if (sideEnumSrc == EntityPoint.Side.TOP ||
        		sideEnumSrc == EntityPoint.Side.BOTTOM) {
            if (type == RelationTypeEnum.AGGREGATION 
                    || type == RelationTypeEnum.COMPOSITION) {
            }
            arrowBody.addPoint(new Point2D(xSrc,
            		Math.max(ySrc, yDst) - Math.abs(yDst - ySrc) / 2));           
            arrowBody.addPoint(new Point2D(xSrc, yDst));
            arrowBody.addPoint(new Point2D(Math.max(xSrc, xDst) -
            		Math.abs(xDst - xSrc) / 2, yDst));
        } else {
            arrowBody.addPoint(new Point2D(Math.min(xSrc, xDst) +
            		Math.abs(xDst - xSrc) / 2, ySrc));
            arrowBody.addPoint(new Point2D(xDst, ySrc));
            arrowBody.addPoint(new Point2D(xDst,
            		Math.max(ySrc, yDst) - Math.abs(yDst - ySrc) / 2));
        }
        drawArrowBodyInGroup();
    }
    
    /**
     * Build a relation composed of three lines.
     */
    private void buildRelationIn3Line() {
        Point2D pointSrc = sideSrc.getPoint();
        Point2D pointDst = sideDst.getPoint();      
        EntityPoint.Side sideEnumSrc = sideSrc.getSide();
         
        double xSrc = pointSrc.getX();
        double ySrc = pointSrc.getY();
        double xDst = pointDst.getX();
        double yDst = pointDst.getY();
        
        clearArrowBodyInGroup();
        arrowBody.clear();
        arrowBody.moveStartPoint(arrowTail.getTailPoint());
        arrowBody.moveEndPoint(arrowHead.getTailPoint());
        arrowTail.setSide(sideEnumSrc);
        arrowHead.setSide(sideDst.getSide());      
        drawArrowBodyInGroup();
        if (sideEnumSrc == EntityPoint.Side.TOP ||
        		sideEnumSrc == EntityPoint.Side.BOTTOM) {
            double y;
            if (sideEnumSrc == EntityPoint.Side.TOP) {
                y = ySrc + (yDst - ySrc) / 2;
            } else {
                y = yDst + (ySrc - yDst) / 2;
            }  
            arrowBody.addPoint(new Point2D(xSrc, y));
            arrowBody.addPoint(new Point2D(Math.min(xSrc, xDst) +
            		Math.abs(xDst - xSrc) / 2, y));
            arrowBody.addPoint(new Point2D(xDst, y));
        } else {
            double x;
            if (sideEnumSrc == EntityPoint.Side.RIGHT) {
                x = xSrc + (xDst - xSrc) / 2;
            } else {
                x = xSrc - (xSrc - xDst) / 2;
            } 
            arrowBody.addPoint(new Point2D(x, ySrc));
            arrowBody.addPoint(new Point2D(x, Math.max(ySrc, yDst) -
            		Math.abs(yDst - ySrc) / 2));
            arrowBody.addPoint(new Point2D(x, yDst));
        }
        drawArrowBodyInGroup();
    }
    
    /**
     * Build a relation between an entity and itself.
     */
    private void buildRelationToSameEntity() { 
        double xSrc = sideSrc.getPoint().getX();
        double ySrc = sideSrc.getPoint().getY();
        double xDst = sideDst.getPoint().getX();
        IEntityGraphic src = getController().getGEntitySrc();
        double hSrc = src.getHeight() / 2;
        double wLine = LINE_SIZE_MIN * 1.5;
        double hLine = LINE_SIZE_MIN * 1.5;     
        clearArrowBodyInGroup();
        arrowBody.clear();  
        arrowBody.moveStartPoint(arrowTail.getTailPoint());
        arrowBody.moveEndPoint(arrowHead.getTailPoint());
        arrowTail.setSide(sideSrc.getSide());
        arrowHead.setSide(sideDst.getSide());
        drawArrowBodyInGroup();
        arrowBody.addPoint(new Point2D(xSrc + wLine, ySrc));
        arrowBody.addPoint(new Point2D(xSrc + wLine, ySrc - hLine - hSrc));
        arrowBody.addPoint(new Point2D(xDst, ySrc - hLine - hSrc));
        drawArrowBodyInGroup();
    }
}
