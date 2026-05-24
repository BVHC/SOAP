
package ptit.dblab.judge.network.server.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="customer">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="customerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="purchaseCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="totalSpent" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customer", propOrder = {
    "customerId",
    "location",
    "purchaseCount",
    "totalSpent"
})
public class Customer {

    protected String customerId;
    protected String location;
    protected int purchaseCount;
    protected float totalSpent;

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerId(String value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the purchaseCount property.
     * 
     */
    public int getPurchaseCount() {
        return purchaseCount;
    }

    /**
     * Sets the value of the purchaseCount property.
     * 
     */
    public void setPurchaseCount(int value) {
        this.purchaseCount = value;
    }

    /**
     * Gets the value of the totalSpent property.
     * 
     */
    public float getTotalSpent() {
        return totalSpent;
    }

    /**
     * Sets the value of the totalSpent property.
     * 
     */
    public void setTotalSpent(float value) {
        this.totalSpent = value;
    }

}
