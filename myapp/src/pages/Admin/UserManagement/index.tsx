import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { ProTable, TableDropdown } from '@ant-design/pro-components';
import React, { useRef } from 'react';
import {searchUsers} from "@/services/ant-design-pro/api";
import { Image } from 'antd';


const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: 'username',
    dataIndex: 'username',
    copyable: true,
    tip: 'resize automatically',
  },
  {
    title: 'User Account',
    dataIndex: 'userAccount',
    copyable: true,
    tip: 'resize automatically',
  },
  {
    title: 'Avatar',
    dataIndex: 'avatar',
    render: (_, record) => (
      <div>
        <Image src={record.avatar} width={100} />
      </div>
    ),

    copyable: true,
    tip: 'resize automatically',
  },
  {
    title: 'Gender',
    dataIndex: 'gender',
    copyable: true,
    tip: 'resize automatically',
  },
  {
    title: 'Email',
    dataIndex: 'email',
    copyable: true,
    tip: 'resize automatically',
  },
  {
    title: 'Phone Number',
    dataIndex: 'phone',
    copyable: true,
    tip: 'resize automatically',
  },
  {
    title: 'User Status',
    dataIndex: 'userStatus',
    tip: 'resize automatically',
  },
  {
    title: 'Account Created Time',
    dataIndex: 'createTime',
    valueType: 'dateTime',
  },
  {
    title: 'Account Updated Time',
    dataIndex: 'updateTime',
    valueType: 'dateTime',
  },
  {
    title: 'Scope',
    dataIndex: 'scope',
    valueType: 'dateTime',
    valueEnum: {
      all: {text: '超长'.repeat(50)},
      0: {
        text: 'User',
        status: 'Default',
      },
      1: {
        text: 'Admin',
        status: 'Success',
        disabled: true,
      },
    },
  },
  {
    title: 'Planet Code',
    dataIndex: 'planetCode',
    width: 48,
  },
  // {
  //   disable: true,
  //   title: '状态',
  //   dataIndex: 'state',
  //   filters: true,
  //   onFilter: true,
  //   ellipsis: true,
  //   valueType: 'select',
  //   valueEnum: {
  //     all: { text: '超长'.repeat(50) },
  //     open: {
  //       text: '未解决',
  //       status: 'Error',
  //     },
  //     closed: {
  //       text: '已解决',
  //       status: 'Success',
  //       disabled: true,
  //     },
  //     processing: {
  //       text: '解决中',
  //       status: 'Processing',
  //     },
  //   },
  // },
  // {
  //   disable: true,
  //   title: '标签',
  //   dataIndex: 'labels',
  //   search: false,
  //   renderFormItem: (_, { defaultRender }) => {
  //     return defaultRender(_);
  //   },
  //   render: (_, record) => (
  //     <Space>
  //       {record.labels.map(({ name, color }) => (
  //         <Tag color={color} key={name}>
  //           {name}
  //         </Tag>
  //       ))}
  //     </Space>
  //   ),
  // },
  // {
  //   title: '创建时间',
  //   key: 'showTime',
  //   dataIndex: 'created_at',
  //   valueType: 'date',
  //   sorter: true,
  //   hideInSearch: true,
  // },
  // {
  //   title: '创建时间',
  //   dataIndex: 'created_at',
  //   valueType: 'dateRange',
  //   hideInTable: true,
  //   search: {
  //     transform: (value) => {
  //       return {
  //         startTime: value[0],
  //         endTime: value[1],
  //       };
  //     },
  //   },
  // },
  {
    title: 'option',
    valueType: 'option',
    key: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"
        onClick={() => {
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>,
      <a href={record.url} target="_blank" rel="noopener noreferrer" key="view">
        查看
      </a>,
      <TableDropdown
        key="actionGroup"
        onSelect={() => action?.reload()}
        menus={[
          { key: 'copy', name: '复制' },
          { key: 'delete', name: '删除' },
        ]}
      />,
    ],
  },
];

export default () => {
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params = {}, sort, filter) => {
        console.log(sort, filter);
        const userList = await searchUsers();
        return {
          data: userList
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      options={{
        setting: {
          listsHeight: 400,
        },
      }}
      form={{
        // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
      headerTitle="高级表格"
    />
  );
};
