/**
 */
package fr.inria.spirals.sigma.ttc14.fixml.objlang;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.Class#getMembers <em>Members</em>}</li>
 *   <li>{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.Class#getSuperclass <em>Superclass</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.spirals.sigma.ttc14.fixml.objlang.ObjLangPackage#getClass_()
 * @model
 * @generated
 */
public interface Class extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Members</b></em>' containment reference list.
	 * The list contents are of type {@link fr.inria.spirals.sigma.ttc14.fixml.objlang.Member}.
	 * It is bidirectional and its opposite is '{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.Member#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Members</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Members</em>' containment reference list.
	 * @see fr.inria.spirals.sigma.ttc14.fixml.objlang.ObjLangPackage#getClass_Members()
	 * @see fr.inria.spirals.sigma.ttc14.fixml.objlang.Member#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<Member> getMembers();

	/**
	 * Returns the value of the '<em><b>Superclass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Superclass</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Superclass</em>' reference.
	 * @see #setSuperclass(Class)
	 * @see fr.inria.spirals.sigma.ttc14.fixml.objlang.ObjLangPackage#getClass_Superclass()
	 * @model required="true"
	 * @generated
	 */
	Class getSuperclass();

	/**
	 * Sets the value of the '{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.Class#getSuperclass <em>Superclass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Superclass</em>' reference.
	 * @see #getSuperclass()
	 * @generated
	 */
	void setSuperclass(Class value);

} // Class
