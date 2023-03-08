import { e } from "../common.js";

const MemberItem = (props) => {
    const { value, tag, roomName, setroomName, recordMap } = props;
    let records = recordMap[value];
    records = records && records[records.length - 1];
    records = records && `⌊${records.speaker}⌉ ${records.text}`;
    return e(tag, { onClick: () => setroomName(value) },
        e('div', { style: { 'font-weight': roomName === value ? 'bold' : 'normal' } }, value),
        e('div', {
            style: {
                'font-weight': 'lighter', 'font-size': 'small',
                'overflow': 'hidden', 'text-overflow': 'ellipsis', 'white-space': 'nowrap',
                'width': '20em'
            }
        }, records));
};

const MemberList = (props) => {
    const { username, mainWorld, members, roomName, setroomName, recordMap } = props;
    const MemberItems = members.filter(member => member !== username)
        .map(other => e(MemberItem, {
            key: other,
            value: other,
            tag: 'li',
            roomName,
            setroomName,
            recordMap
        }));
    return e('div', { style: { margin: '1em', padding: '1em', height: '12em', overflow: 'auto' } },
        e(MemberItem, {
            value: mainWorld,
            tag: 'div',
            roomName,
            setroomName,
            recordMap
        }),
        e('ul', { style: { margin: 0, padding: 0 } }, MemberItems));
};

export { MemberList };