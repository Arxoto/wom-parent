import { e } from "../common.js";
import { Editor } from "./editor.js";

const RecordItem = (props) => {
    return e('li', {}, props.record.speaker + ': ' + props.record.text);
};

const RecordList = (props) => {
    const { username, records } = props;
    const MemberItems = records && records.map((record, index) => e(RecordItem, {
        key: index,
        record,
        username,
    }));
    return e('ul', { style: { margin: 0, padding: 0 } }, MemberItems);
};

const ChatRoom = (props) => {
    const { username, roomName, recordMap, sendMessage } = props;
    return e('div', {},
        e('h1', {}, roomName),
        e(RecordList, { username, records: recordMap[roomName] }),
        e(Editor, { sendMessage }),
    );
};

export { ChatRoom };