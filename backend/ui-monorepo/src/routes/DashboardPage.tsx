import { useMemo, useState } from "react"
import Dashboard from "../client-dashboard/dashboard/Dashboard"
import { CreateOrganization, OrganizationSwitcher, useOrganizationList } from "@clerk/clerk-react";


export const DashboardPage = () => {
    const { setActive, isLoaded, organizationList } = useOrganizationList()
    const [hasOrg, setHasOrg] = useState<boolean>(false);
    useMemo(() => {
        if (isLoaded) {
            console.log(organizationList)
            if (organizationList[0]) {
                setActive({organization: organizationList[0].organization.id})
                setHasOrg(true)
            }
        }
    }, [isLoaded])

    return (
        <>
            {hasOrg ?
                <>
                    <Dashboard />
                </>
                : <CreateOrganization />}
        </>
    )
}
