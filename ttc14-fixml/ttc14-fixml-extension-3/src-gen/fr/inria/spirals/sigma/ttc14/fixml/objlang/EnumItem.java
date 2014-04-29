/**
 */
package fr.inria.spirals.sigma.ttc14.fixml.objlang;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.EnumItem#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.spirals.sigma.ttc14.fixml.objlang.ObjLangPackage#getEnumItem()
 * @model
 * @generated
 */
public interface EnumItem extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum)
	 * @see fr.inria.spirals.sigma.ttc14.fixml.objlang.ObjLangPackage#getEnumItem_Parent()
	 * @see fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum#getItems
	 * @model opposite="items" required="true"
	 * @generated
	 */
	fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum getParent();

	/**
	 * Sets the value of the '{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.EnumItem#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum value);

} // EnumItem
