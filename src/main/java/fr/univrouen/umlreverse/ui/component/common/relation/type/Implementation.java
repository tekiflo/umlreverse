/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univrouen.umlreverse.ui.component.common.relation.type;

import fr.univrouen.umlreverse.ui.component.common.relation.type.RelationTypeEnum;
import fr.univrouen.umlreverse.ui.component.common.relation.type.RelationType;
import javafx.scene.shape.Line;

public class Implementation extends RelationType {
        public Implementation() {
            super();
            Line l1 = new Line(0, 0, 30, 0);
            l1.getStrokeDashArray().add(5d);
            Line l2 = new Line(30, 0, 30, -8);
            Line l3 = new Line(30, -8, 40, 0);
            Line l4 = new Line(40, 0, 30, 8);
            Line l5 = new Line(30, 8, 30, 0);

            getChildren().add(l1);
            getChildren().add(l2);
            getChildren().add(l3);
            getChildren().add(l4);
            getChildren().add(l5);
        }
        
        @Override
        public RelationTypeEnum getType() {
            return RelationTypeEnum.IMPLEMENTATION;
        }
    }
