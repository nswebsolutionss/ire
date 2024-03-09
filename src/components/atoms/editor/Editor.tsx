import { useState } from "react";
import QuillEditor from "react-quill";

import "react-quill/dist/quill.snow.css";

interface EditorProps {
  value: string;
  onValueChange: (val: string) => void
}
export const Editor: React.FC<EditorProps> = ({value, onValueChange}) => {

  return (
    <div style={{width: "100%", height: "375px", marginTop: "15px"}}>
      <QuillEditor
        style={{height: "300px"}}
        theme="snow"
        value={value}
        onChange={(value) => onValueChange(value)}
      />
    </div>
  );
};

export default Editor;