import { useMemo } from "react";
import { Button, Label, VerticalSpacer } from "@dwellio/components";
import { useUser } from "../user-slice/useUserStateSelectors";

export const MembershipSection: React.FC = () => {

    const user = useUser();
    const dateAddedMs = new Date(user.membership.memberSinceMs);
    const year = dateAddedMs.getUTCFullYear();
    const month = dateAddedMs.getUTCMonth();
    const day = dateAddedMs.getUTCDay();

    return (
        <div style={{ width: "100%", padding: "25px", display: "flex", alignItems: "center", flexDirection: "column" }}>
            <p style={{ fontFamily: "Nunito Sans", fontSize: "35px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", margin: "0px" }}>Membership</p>
            <VerticalSpacer size={"5"} />
            <p style={{ paddingLeft: "1px", fontFamily: "Nunito Sans", fontSize: "18px", fontWeight: "400", color: "rgba(0, 0,0 ,0.3)", margin: "0px" }}>View and modify your membership here</p>
            <VerticalSpacer />
            <p style={{ margin: "0px", fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)" }}>Your Membership</p>
            <VerticalSpacer />

            <div style={{ display: "flex", flexDirection: "row", borderTop: "1px solid rgba(0, 0, 0, 0.2)", borderBottom: "1px solid rgba(0, 0, 0, 0.2)", height: "auto", padding: "20px", backgroundColor: "rgba(0, 0, 0, 0.03)" }}>
                <Label style={{ width: "150px", textAlign: "center" }}>Level</Label>
                <Label style={{ width: "150px", textAlign: "center" }}>Billing</Label>
                <Label style={{ width: "150px", textAlign: "center" }}>Member Since</Label>
                <Label style={{ width: "150px", textAlign: "center" }}>Expiration</Label>

            </div>
            <div style={{ display: "flex", flexDirection: "row", borderBottom: "1px solid rgba(0, 0, 0, 0.2)", height: "auto", padding: "20px" }}>
                <OpeningTimeRow value1={user.membership.level} value2={user.membership.billingPeriod} value3={`${day}-${month}-${year}`} value4={""} />
            </div>
            <Button style={{ fontSize: "18px", color: "#2044a8", backgroundColor: "white", border: "1px solid #2044a8" }}>View All Memberships Options</Button>

            <VerticalSpacer />
            <p style={{ margin: "0px", fontFamily: "inherit", fontSize: "26px", fontWeight: "500", color: "rgba(0, 0,0 ,0.4)", alignSelf: "center" }}>Past Invoices</p>
            <VerticalSpacer />

            <div style={{ display: "flex", flexDirection: "row", borderTop: "1px solid rgba(0, 0, 0, 0.2)", borderBottom: "1px solid rgba(0, 0, 0, 0.2)", height: "auto", padding: "20px", backgroundColor: "rgba(0, 0, 0, 0.03)" }}>
                <Label style={{ width: "150px", textAlign: "center" }}>Date</Label>
                <Label style={{ width: "150px", textAlign: "center" }}>Level</Label>
                <Label style={{ width: "150px", textAlign: "center" }}>Amount</Label>
                <Label style={{ width: "150px", textAlign: "center" }}>Status</Label>

            </div>

            {user.membership.invoices.map((invoice) => {
                    const invoiceDate = new Date(invoice.date);
                    const year = invoiceDate.getUTCFullYear();
                    const month = invoiceDate.getUTCMonth();
                    const day = invoiceDate.getUTCDay();

                return (
                    <div key={invoice.date} style={{ display: "flex", flexDirection: "row", borderBottom: "1px solid rgba(0, 0, 0, 0.2)", height: "auto", padding: "20px" }}>
                        <OpeningTimeRow value1={`${day}-${month}-${year}`} value2={invoice.level} value3={invoice.amount} value4="Paid" />
                    </div>
                )
            })}


        </div>

    )
}

const OpeningTimeRow: React.FC<{ value1: string, value2: string, value3: string, value4: string, }> = ({ value1, value2, value3, value4 }) => {


    return (
        <>
            <Label style={{ width: "150px", textAlign: "center" }}>{value1}</Label>
            <Label style={{ width: "150px", textAlign: "center" }}>{value2}</Label>
            <Label style={{ width: "150px", textAlign: "center" }}>{value3}</Label>
            <Label style={{ width: "150px", textAlign: "center" }}>{value4}</Label>
        </>
    )
}