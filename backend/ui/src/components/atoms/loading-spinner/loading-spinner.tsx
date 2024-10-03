import React from "react"
import { ClipLoader } from "react-spinners"

export const LoadingSpinner: React.FC = () => {
    return (
        <div style={{position: "absolute", display: "flex", justifyContent: "center", alignItems: "center",height: "100vh", width: "100vw", backgroundColor: "rgba(255, 255, 255, 0.4)"}}>
            <ClipLoader color="#2044a8" size={400} />
        </div>
    )
}