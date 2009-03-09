/**
 * Mogwai ERDesigner. Copyright (C) 2002 The Mogwai Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package de.erdesignerng.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author $Author: mirkosertic $
 * @version $Date: 2009-03-09 19:07:29 $
 */
public class RelationList extends ModelItemVector<Relation> {

    private static final long serialVersionUID = 330168987165235683L;

    /**
     * Check if a table is used by any of the defined relations.
     * 
     * @param aTable
     *            the table
     * @return true it its used, else false
     */
    public boolean isTableInUse(Table aTable) {
        for (Relation theRelation : this) {
            if (theRelation.getImportingTable().equals(aTable)) {
                return true;
            }
            if (theRelation.getExportingTable().equals(aTable)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Check if an attribute is used by any of the defined relations.
     * 
     * @param aAttribute
     *            the attribute
     * @return true if its in use by a relation, else false
     */
    public boolean isAttributeInUse(Attribute aAttribute) {
        for (Relation theRelation : this) {
            Map<IndexExpression, Attribute> theMap = theRelation.getMapping();
            for (IndexExpression theExpression : theMap.keySet()) {
                if (theExpression.getAttributeRef() != null) {
                    if (theExpression.getAttributeRef().equals(aAttribute)) {
                        return true;
                    }
                }
            }
            if (theMap.containsValue(aAttribute)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test if an attribute is a foreign key attribute. 
     * 
     * @param aAttribute the attribute
     * @return true if yes, else false
     */
    public boolean isForeignKeyAttribute(Attribute aAttribute) {
        for (Relation theRelation : this) {
            Map<IndexExpression, Attribute> theMap = theRelation.getMapping();
            if (theMap.containsValue(aAttribute)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove all relations that are connected to a given table.
     * 
     * @param aTable
     *            the table
     */
    public void removeByTable(Table aTable) {
        List<Relation> theRelationsToRemove = new ArrayList<Relation>();
        for (Relation theRelation : this) {
            if (theRelation.getImportingTable().equals(aTable)) {
                theRelationsToRemove.add(theRelation);
            } else {
                if (theRelation.getExportingTable().equals(aTable)) {
                    theRelationsToRemove.add(theRelation);
                }
            }
        }
        removeAll(theRelationsToRemove);
    }

    public List<Relation> getForeignKeysFor(Table aTable) {
        List<Relation> theResult = new ArrayList<Relation>();
        for (Relation theRelation : this) {
            if (theRelation.getImportingTable().equals(aTable)) {
                theResult.add(theRelation);
            }
        }
        return theResult;
    }

    public List<Relation> getExportedKeysFor(Table aTable) {
        List<Relation> theResult = new ArrayList<Relation>();
        for (Relation theRelation : this) {
            if (theRelation.getExportingTable().equals(aTable)) {
                theResult.add(theRelation);
            }
        }
        return theResult;
    }

}
