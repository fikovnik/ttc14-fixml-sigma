/**
 */
package fr.inria.spirals.sigma.ttc14.fixml.objlang.impl;

import fr.inria.spirals.sigma.ttc14.fixml.objlang.EnumItem;
import fr.inria.spirals.sigma.ttc14.fixml.objlang.ObjLangPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link fr.inria.spirals.sigma.ttc14.fixml.objlang.impl.EnumItemImpl#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumItemImpl extends NamedElementImpl implements EnumItem
{
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum parent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumItemImpl()
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
		return ObjLangPackage.Literals.ENUM_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum getParent()
	{
		if (parent != null && parent.eIsProxy())
		{
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum)eResolveProxy(oldParent);
			if (parent != oldParent)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ObjLangPackage.ENUM_ITEM__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum basicGetParent()
	{
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum newParent, NotificationChain msgs)
	{
		fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ObjLangPackage.ENUM_ITEM__PARENT, oldParent, newParent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum newParent)
	{
		if (newParent != parent)
		{
			NotificationChain msgs = null;
			if (parent != null)
				msgs = ((InternalEObject)parent).eInverseRemove(this, ObjLangPackage.ENUM__ITEMS, fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum.class, msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, ObjLangPackage.ENUM__ITEMS, fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ObjLangPackage.ENUM_ITEM__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case ObjLangPackage.ENUM_ITEM__PARENT:
				if (parent != null)
					msgs = ((InternalEObject)parent).eInverseRemove(this, ObjLangPackage.ENUM__ITEMS, fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum.class, msgs);
				return basicSetParent((fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum)otherEnd, msgs);
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
			case ObjLangPackage.ENUM_ITEM__PARENT:
				return basicSetParent(null, msgs);
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
			case ObjLangPackage.ENUM_ITEM__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case ObjLangPackage.ENUM_ITEM__PARENT:
				setParent((fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum)newValue);
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
			case ObjLangPackage.ENUM_ITEM__PARENT:
				setParent((fr.inria.spirals.sigma.ttc14.fixml.objlang.Enum)null);
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
			case ObjLangPackage.ENUM_ITEM__PARENT:
				return parent != null;
		}
		return super.eIsSet(featureID);
	}

} //EnumItemImpl
