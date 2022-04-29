"""
    the script swap out the places in a turtle file where the prefix core: is used, or the full namespace of perm and device.

    after running the script, correct the namespace declarations manually (the correct namespaces are now given in the apso turle files or in the thesis)

    run the script in terminal.
"""

in_path = "./PLACEHOLDER.ttl"
out_path = "./PLACEHOLDER2.ttl"

namespace  = {'perm': '<http://www.semanticweb.org/ontologies/2022/1/apso/perm#',
    'device': '<http://www.semanticweb.org/ontologies/2022/1/apso/device#'}

# replace prefixes of the entities.
def replace_uri(line):
    need_replacement = namespace['perm'] in line or namespace['device'] or 'core' in line
    if not need_replacement: return line
    lst = line.split()
    for i in range(len(lst)):
        term = lst[i]
        if term.startswith(namespace['perm']):
            term = term.replace(namespace['perm'], 'perm:')
            term = term.replace('>','')
        elif term.startswith(namespace['device']):
            term = term.replace(namespace['device'], 'device:')
            term = term.replace('>','')
        elif term.startswith('core'):
            term = term.replace('core', '')
        lst[i] = term
    new_line = ' '.join(lst)
    return new_line

document = ''

if __name__ == '__main__':
    print('running script.')
    with open(in_path, 'r', encoding='UTF-8') as file:
        for line in file:
            ttl_line = replace_uri(line)
            document += ttl_line
            if line != ttl_line: document += '\n'

    with open(out_path, 'w', encoding='UTF-8') as file:
        file.write(document)
    print('finnished script.')
