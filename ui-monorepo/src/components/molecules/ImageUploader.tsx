import { useEffect, useState } from "react";
import { TiDelete } from "react-icons/ti";
import styled from "styled-components";
import { useImageStateDispatch } from "../../client-dashboard/images-slice/useImageStateDispatch";
import { useAllImages } from "../../client-dashboard/images-slice/useImageStateSelectors";
import { Label } from "../atoms/FormFields";
import { TextInput } from "../atoms/TextInput";
import { IoIosArrowDown } from "react-icons/io";
import { IoIosArrowUp } from "react-icons/io";
import { PropertyImageState } from "../../client-dashboard/images-slice/imagesSlice";


export type ImageArray = Blob | MediaSource

export interface DropContainerInterface {
    hovered: boolean
}
export const DropContainer = styled.div<DropContainerInterface>`
    margin-bottom: 25px;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 150px;
    width: 100%;
    border: 1px ${p => p.hovered ? "dashed" : "solid"} rgba(0, 0, 0, 0.2);
    border-radius: 5px
`

export const Container = styled.div`
    font-size: 14px;
    margin-top: 5px;
    color: rgba(0, 0, 0, 0.5);
    padding: 5px;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    flex-direction: row;
    width: 100%;
    height: 140px;
`

export const ImageUploader: React.FC<{ cleanImages: PropertyImageState[] | null; }> = ({ cleanImages }) => {
    const { upsertImage, upsertImages, deleteImageById } = useImageStateDispatch()
    const allImages = useAllImages();

    let i = 1;
    const [hovered, setHovered] = useState<boolean>(false);

    if (cleanImages !== null && cleanImages.length > 0) {
        if (allImages === null || allImages.length === 0) {
            upsertImages(cleanImages)
        }
    }

    const sortedImages = [...allImages].sort((a, b) => a.index > b.index ? 1 : -1);
    function dropHandler(e: React.DragEvent<HTMLDivElement>) {
        setHovered(false)
        e.preventDefault();
        const index = allImages.length

        upsertImage({
            image: e.dataTransfer.files[0],
            id: Date.now(),
            index: index,
            isCover: index === 0,
        })
    }


    function dragOverHandler(ev: React.DragEvent<HTMLDivElement>) {
        console.log("File(s) in drop zone");
        setHovered(true)
        ev.preventDefault();
    }

    return (
        <div style={{ display: "flex", flexDirection: "column", height: "auto", width: "100%", alignItems: "center" }}>
            <DropContainer
                hovered={hovered}
                id="drop_zone"
                onDrop={(e) => dropHandler(e)}
                onDragOver={(e) => dragOverHandler(e)}
                onDragExitCapture={() => setHovered(false)}
            >
                <p>Drag one or more files to this <i>drop zone</i>.</p>
            </DropContainer>
            {
                sortedImages.map(image => {
                    if (image !== undefined && image.image !== null) {
                        return (
                            <Container key={image.id} >
                                <div style={{ display: "flex", flexDirection: "column" }}>
                                </div>
                                <div style={{ display: "flex", flexDirection: "column", alignItems: "center", height: "100%", padding: "0px" }}>
                                    <Label style={{ color: "inherit", margin: "0px" }}>Image</Label>
                                    <div style={{ height: "100%", display: "flex", alignItems: "center", justifyContent: "center" }}>
                                        <img
                                            style={{ justifySelf: "flex-end" }}
                                            alt="not found"
                                            width={"150px"}
                                            height={"100px"}
                                            src={URL.createObjectURL(image.image)}
                                        />
                                    </div>
                                </div>
                                <div style={{ display: "flex", flexDirection: "column", justifyContent: "start", height: "100%" }}>
                                    <Label style={{ color: "inherit", margin: "0px" }}>Title</Label>
                                    <div style={{ height: "100%", display: "flex", alignItems: "center", justifyContent: "center" }}>
                                        <TextInput name="title" width="200px" value={image.title ?? ""} onValueChange={(e) => {
                                            upsertImage({
                                                image: image.image,
                                                id: image.id,
                                                index: image.index,
                                                isCover: image.isCover,
                                                title: e.target.value,
                                            })
                                        }}></TextInput>
                                    </div>
                                </div>
                                <div style={{ display: "flex", flexDirection: "column", justifyContent: "start", height: "100%" }}>
                                    <Label style={{ color: "inherit", margin: "0px" }}>Cover Photo</Label>
                                    <div style={{ height: "100%", display: "flex", alignItems: "center", justifyContent: "center" }}>
                                        <input type="radio" name="image" value="cover photo" style={{ justifySelf: "flex-end" }} checked={image.isCover === true} onClick={
                                            () => {
                                                sortedImages.map(imageInArray => {
                                                    if (imageInArray !== undefined && imageInArray.image !== null) {
                                                        if (imageInArray.id !== image.id) {
                                                            upsertImage({
                                                                image: imageInArray.image,
                                                                id: imageInArray.id,
                                                                index: imageInArray.index,
                                                                isCover: false,
                                                                title: imageInArray.title,
                                                            })
                                                        }
                                                        else {
                                                            upsertImage({
                                                                image: imageInArray.image,
                                                                id: imageInArray.id,
                                                                index: imageInArray.index,
                                                                isCover: true,
                                                                title: imageInArray.title,
                                                            })
                                                        }
                                                    }
                                                })

                                            }}></input>
                                    </div>
                                </div>
                                <div style={{ display: "flex", flexDirection: "column", justifyContent: "start", height: "100%" }}>
                                    <Label style={{ color: "inherit", margin: "0px" }} >Remove</Label>
                                    <div style={{ height: "100%", display: "flex", alignItems: "center", justifyContent: "center" }} onClick={() => {
                                        deleteImageById(image.id)
                                    }}>
                                        <TiDelete style={{ justifySelf: "flex-end" }} size={18} />
                                    </div>
                                </div>
                            </Container>
                        )
                    }
                })
            }

        </div>


    )
}
