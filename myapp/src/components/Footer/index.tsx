import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import { useIntl } from 'umi';

const Footer: React.FC = () => {
  const intl = useIntl();
  // const defaultMessage = intl.formatMessage({
  //   id: 'app.copyright.produced',
  //   defaultMessage: 'Produced by ME',
  // });
  const defaultMessage = 'Produced by ME';
  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Project',
          title: 'User Client Project',
          href: 'https://github.com/YamataHermione',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined /> My Github</>,
          href: 'https://github.com/YamataHermione',
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: 'Ant Design',
          href: 'https://ant.design',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
