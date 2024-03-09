
import { NavElement } from "../navigation-slice/navigationSlice";
import { useSelectedNavElement } from "../navigation-slice/useNavigationStateSelectors";
import { DashboardNavBar } from '../navigation/DashboardNavBar';
import { ProfileSection } from "../profile/ProfileSection";

import "ag-grid-community/styles/ag-grid.css"; // Mandatory CSS required by the grid
import "ag-grid-community/styles/ag-theme-quartz.css"; // Optional Theme applied to the grid
import { PropertySection } from "../property/PropertySection";
import { MembershipSection } from "../membership/MembershipSection";

export const Dashboard: React.FC = () => {
  return (
    <div style={{ width: "100vw", height: "100vh", display: "flex", fontFamily: "Nunito Sans", fontWeight: "500", position: "relative" }}>
      <DashboardNavBar />
      <div style={{ height: "100%", width: "100%", backgroundColor: "#f0f0f0", padding: "25px", boxSizing: "border-box", display: "flex" }}>
        <div style={{ height: "100%", width: "100%", backgroundColor: "white", padding: "25px", borderRadius: "5px", boxSizing: "border-box", display: "flex", justifyContent: "space-around", flexWrap: "wrap", overflowY: "scroll" }}>
          {useSelectedNavElement() === NavElement.Properties ? <PropertySection /> : <></>}
          {useSelectedNavElement() === NavElement.Profile ? <ProfileSection /> : <></>}
          {useSelectedNavElement() === NavElement.Membership ? <MembershipSection /> : <></>}
        </div>
    

      </div>
    </div>
  );
}

export default Dashboard;

/**
 * 
 * property: 
 *  - id
 *  - address
 *  - price
 *  - dateadded
 *  - property type
 *  - country
 *  - beds
 *  - bathrooms
 *  - photos
 * 
 * user: 
 *  - membership
 *    - level
 *    - billing period
 *    - member since
 *    - membership expiration
 *    - invoices:
 *      - date
 *      - level
 *      - amount
 *      - status
 *  - profile
 *     - details
 *       - company name
 *       - email address
 *       - telephone number
 *       - website url
 *       - facebook url
 *       - instagram url
 *       - description
 *    - media
 *      - youtube url
 *      - images
 *    - opening times
 *      - day
 *      - open time
 *      - close time
 *      - closed
 *      - 24h
 * 
 *  - 
 * 
 */