/**
 */
package fr.inria.spirals.sigma.ttc14.fixml.objlang.impl;

import fr.inria.spirals.sigma.ttc14.fixml.objlang.Member;
import fr.inria.spirals.sigma.ttc14.fixml.objlang.ObjLangPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.impl.ClassImpl#getMembers <em>Members</em>}</li>
 *   <li>{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.impl.ClassImpl#getSuperclass <em>Superclass</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassImpl extends NamedElementImpl implements fr.inria.spirals.sigma.ttc14.fixml.objlang.Class
{
	/**
	 * The cached value of the '{@link #getMembers() <em>Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<Member> members;

	/**
	 * The cached value of the '{@link #getSuperclass() <em>Superclass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperclass()
	 * @generated
	 * @ordered
	 */
	protected fr.inria.spirals.sigma.ttc14.fixml.objlang.Class superclass;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return ObjLangPackage.Literals.CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Member> getMembers()
	{
		if (members == null)
		{
			members = new EObjectContainmentWithInverseEList<Member>(Member.class, this, ObjLangPackage.CLASS__MEMBERS, ObjLangPackage.MEMBER__PARENT);
		}
		return members;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public fr.inria.spirals.sigma.ttc14.fixml.objlang.Class getSuperclass()
	{
		if (superclass != null && superclass.eIsProxy())
		{
			InternalEObject oldSuperclass = (InternalEObject)superclass;
			superclass = (fr.inria.spirals.sigma.ttc14.fixml.objlang.Class)eResolveProxy(oldSuperclass);
			if (superclass != oldSuperclass)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ObjLangPackage.CLASS__SUPERCLASS, oldSuperclass, superclass));
			}
		}
		return superclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public fr.inria.spirals.sigma.ttc14.fixml.objlang.Class basicGetSuperclass()
	{
		return superclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperclass(fr.inria.spirals.sigma.ttc14.fixml.objlang.Class newSuperclass)
	{
		fr.inria.spirals.sigma.ttc14.fixml.objlang.Class oldSuperclass = superclass;
		superclass = newSuperclass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ObjLangPackage.CLASS__SUPERCLASS, oldSuperclass, superclass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case ObjLangPackage.CLASS__MEMBERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getMembers()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case ObjLangPackage.CLASS__MEMBERS:
				return ((InternalEList<?>)getMembers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case ObjLangPackage.CLASS__MEMBERS:
				return getMembers();
			case ObjLangPackage.CLASS__SUPERCLASS:
				if (resolve) return getSuperclass();
				return basicGetSuperclass();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case ObjLangPackage.CLASS__MEMBERS:
				getMembers().clear();
				getMembers().addAll((Collection<? extends Member>)newValue);
				return;
			case ObjLangPackage.CLASS__SUPERCLASS:
				setSuperclass((fr.inria.spirals.sigma.ttc14.fixml.objlang.Class)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case ObjLangPackage.CLASS__MEMBERS:
				getMembers().clear();
				return;
			case ObjLangPackage.CLASS__SUPERCLASS:
				setSuperclass((fr.inria.spirals.sigma.ttc14.fixml.objlang.Class)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case ObjLangPackage.CLASS__MEMBERS:
				return members != null && !members.isEmpty();
			case ObjLangPackage.CLASS__SUPERCLASS:
				return superclass != null;
		}
		return super.eIsSet(featureID);
	}

} //ClassImpl
