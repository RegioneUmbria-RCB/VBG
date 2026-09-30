/*
 * Copyright (C) 2014 Ivan Krivyakov, http://www.ikriv.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */
using System.Reflection;
using System.Xml.Serialization;

//namespace Ikriv.Xml
namespace VBG.Backend.Protocollo.AppLogic.Shared.Utils
{
    /// <summary>
    /// Creates XmlAttributeOverrides instance using an easy-to-use fluent interface
    /// </summary>
    public class OverrideXml
    {
        private Type _currentType;
        private string _currentMember = "";
        private XmlAttributes _attributes;
        private readonly XmlAttributeOverrides _overrides = new XmlAttributeOverrides();

        /// <summary>
        /// Specifies that subsequent attributes wil be applied to type t
        /// </summary>
        public OverrideXml Override(Type t)
        {
            this.Commit();
            this._currentType = t;
            this._currentMember = "";
            return this;
        }

        /// <summary>
        /// Specifies that subsequent attributes wil be applied to type T
        /// </summary>
        public OverrideXml Override<T>()
        {
            return this.Override(typeof(T));
        }

        /// <summary>
        /// Specifies that subsequent attributes wil be applied to the given member of the current type
        /// </summary>
        public OverrideXml Member(string name)
        {
            this.Commit();
            if (this._currentType == null) throw new InvalidOperationException("Current type is not defined. Use Override<T>() to define current type");

            // attempt to verify that such member indeed exists
            const BindingFlags flags = BindingFlags.Instance | BindingFlags.Public;
            if (this._currentType.GetProperty(name, flags) == null && this._currentType.GetField(name, flags) == null)
            {
                throw new InvalidOperationException("Property or field '" + name + "' does not exist in type " + this._currentType.Name + " or is not public");
            }

            this._currentMember = name;
            return this;
        }

        /// <summary>
        /// Constructs XmlAttributeOverrides instance from previously specified attributes
        /// </summary>
        public XmlAttributeOverrides Commit()
        {
            if (this._attributes != null)
            {
                this._overrides.Add(this._currentType, this._currentMember, this._attributes);
                this._currentMember = "";
                this._attributes = null;
            }

            return this._overrides;
        }

        /// <summary>
        /// Adds [XmlRoot(elementName)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlRoot(string elementName)
        {
            this.Open();
            this._attributes.XmlRoot = new XmlRootAttribute(elementName);
            return this;
        }

        /// <summary>
        /// Adds specified instance of XmlRootAttribute for current type or member
        /// </summary>
        public OverrideXml Attr(XmlRootAttribute xmlRoot)
        {
            this.Open();
            this._attributes.XmlRoot = xmlRoot;
            return this;
        }

        /// <summary>
        /// Adds [XmlAttribute] attribute to current type or member
        /// </summary>
        public OverrideXml XmlAttribute()
        {
            this.Open();
            this._attributes.XmlAttribute = new XmlAttributeAttribute();
            return this;
        }

        /// <summary>
        /// Adds [XmlAttribute(name)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlAttribute(string name)
        {
            this.Open();
            this._attributes.XmlAttribute = new XmlAttributeAttribute(name);
            return this;
        }

        /// <summary>
        /// Adds specified instance of XmlAttributeAttribute to current type or member
        /// </summary>
        public OverrideXml Attr(XmlAttributeAttribute attribute)
        {
            this.Open();
            this._attributes.XmlAttribute = attribute;
            return this;
        }

        /// <summary>
        /// Adds [XmlElement] attribute to current type or member
        /// </summary>
        public OverrideXml XmlElement()
        {
            this.Open();
            this._attributes.XmlElements.Add(new XmlElementAttribute());
            return this;
        }

        /// <summary>
        /// Adds [XmlElement(name)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlElement(string name)
        {
            this.Open();
            this._attributes.XmlElements.Add(new XmlElementAttribute(name));
            return this;
        }

        /// <summary>
        /// Adds specified instance of XmlElementAttribute to current type or member
        /// </summary>
        public OverrideXml Attr(XmlElementAttribute attribute)
        {
            this.Open();
            this._attributes.XmlElements.Add(attribute);
            return this;
        }

        /// <summary>
        /// Adds [XmlIgnore] attribute to current type or member
        /// </summary>
        /// <param name="bIgnore"></param>
        public OverrideXml XmlIgnore(bool bIgnore = true)
        {
            this.Open();
            this._attributes.XmlIgnore = bIgnore;
            return this;
        }

        /// <summary>
        /// Adds [XmlAnyAttribute] attribute to current type or member
        /// </summary>
        public OverrideXml XmlAnyAttribute()
        {
            this.Open();
            this._attributes.XmlAnyAttribute = new XmlAnyAttributeAttribute();
            return this;
        }

        /// <summary>
        /// Adds [XmlAnyElement] attribute to current type or member
        /// </summary>
        public OverrideXml XmlAnyElement()
        {
            this.Open();
            this._attributes.XmlAnyElements.Add(new XmlAnyElementAttribute());
            return this;
        }

        /// <summary>
        /// Adds [XmlAnyElement(name)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlAnyElement(string name)
        {
            this.Open();
            this._attributes.XmlAnyElements.Add(new XmlAnyElementAttribute(name));
            return this;
        }

        /// <summary>
        /// Adds [XmlAnyElement(name,ns)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlAnyElement(string name, string ns)
        {
            this.Open();
            this._attributes.XmlAnyElements.Add(new XmlAnyElementAttribute(name, ns));
            return this;
        }

        /// <summary>
        /// Adds specified instance of XmlAnyElementAttribute to current type or member
        /// </summary>
        public OverrideXml Attr(XmlAnyElementAttribute attribute)
        {
            this.Open();
            this._attributes.XmlAnyElements.Add(attribute);
            return this;
        }

        /// <summary>
        /// Adds [XmlArray] attribute to current type or memeber
        /// </summary>
        public OverrideXml XmlArray()
        {
            this.Open();
            this._attributes.XmlArray = new XmlArrayAttribute();
            return this;
        }

        /// <summary>
        /// Adds [XmlArray(elementName)] attribute to current type or memeber
        /// </summary>
        public OverrideXml XmlArray(string elementName)
        {
            this.Open();
            this._attributes.XmlArray = new XmlArrayAttribute(elementName);
            return this;
        }

        /// <summary>
        /// Adds specified instance of XmlArrayAttribute to current type or memeber
        /// </summary>
        public OverrideXml Attr(XmlArrayAttribute attribute)
        {
            this.Open();
            this._attributes.XmlArray = attribute;
            return this;
        }

        /// <summary>
        /// Adds [XmlArrayItem] attribute to current type or member
        /// </summary>
        public OverrideXml XmlArrayItem()
        {
            this.Open();
            this._attributes.XmlArrayItems.Add(new XmlArrayItemAttribute());
            return this;
        }

        /// <summary>
        /// Adds [XmlArrayItem(elementName)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlArrayItem(string elementName)
        {
            this.Open();
            this._attributes.XmlArrayItems.Add(new XmlArrayItemAttribute(elementName));
            return this;
        }

        /// <summary>
        /// Adds specified instance of XmlArrayItemAttribute to current type or member
        /// </summary>
        public OverrideXml Attr(XmlArrayItemAttribute attribute)
        {
            this.Open();
            this._attributes.XmlArrayItems.Add(attribute);
            return this;
        }

        /// <summary>
        /// Adds [XmlDefault(value)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlDefaultValue(object value)
        {
            this.Open();
            this._attributes.XmlDefaultValue = value;
            return this;
        }

        /// <summary>
        /// Applies or removes [XmlNamespaceDeclarations] attribute from current type or member
        /// </summary>
        public OverrideXml Xmlns(bool value)
        {
            this.Open();
            this._attributes.Xmlns = value;
            return this;
        }

        /// <summary>
        /// Adds [XmlText] attribute to current type or member
        /// </summary>
        public OverrideXml XmlText()
        {
            this.Open();
            this._attributes.XmlText = new XmlTextAttribute();
            return this;
        }

        /// <summary>
        /// Adds [XmlType(typeName)] attribute to current type or member
        /// </summary>
        public OverrideXml XmlType(string typeName)
        {
            this.Open();
            this._attributes.XmlType = new XmlTypeAttribute(typeName);
            return this;
        }

        /// <summary>
        /// Adds specified instance of XmlTypeAttribute to current type or member
        /// </summary>
        public OverrideXml Attr(XmlTypeAttribute attribute)
        {
            this.Open();
            this._attributes.XmlType = attribute;
            return this;
        }

        private void Open()
        {
            if (this._attributes == null) this._attributes = new XmlAttributes();
        }
    }
}