# Altran Kas Test

Proyecto que consume una api rest del Ayuntamiento de Barcelona con fines didácticos y expone code, organization, description y URI 

Se divide en 3 modulos:`beans`, `model`, y `webcontroller`

## `Beans`
Uso de clases generales y entidades, también puede incluir algunas clases utiles (Fechas, I/O, Formateo de texto y números)
  
## `Model`
Logica de negocio que hace uso del modulo (`beans`)

## `Webcontroller`
 
Aplicacion que muestra el contenido de la prueba que hace uso de spring mvc y conecta con (`model`).

## Compilacion y entregable 
 
Hace uso de maven para la compilacion y generación de entregables:

* **mvn clean compile** para compilar
* **mvn clean package** para generar el entregable en format jar
* **mvn clean package -P war** para generar el entregable en format war

## Ejecucción
    
En ambas versiones (jar/war) se puede ejecutar mediante:

**java -jar webcontroller/target/webcontroller.{jar/war}**

## Fuente de datos
(http://opendata-ajuntament.barcelona.cat/data/api/3/action/package_search)

## Ayuda a la hora de construir la aplicacion

(http://opendata-ajuntament.barcelona.cat/data/api/3/action/help_show?name=package_search)

 Searches for packages satisfying a given search criteria.\n\n    This action accepts solr search query parameters (details below), and\n    returns a dictionary of results, including dictized datasets that match\n    the search criteria, a search count and also facet information.\n\n    **Solr Parameters:**\n\n    For more in depth treatment of each paramter, please read the `Solr\n    Documentation <http://wiki.apache.org/solr/CommonQueryParameters>`_.\n\n    This action accepts a *subset* of solr's search query parameters:\n\n\n    :param q: the solr query.  Optional.  Default: ``\"*:*\"``\n    :type q: string\n    :param fq: any filter queries to apply.  Note: ``+site_id:{ckan_site_id}``\n        is added to this string prior to the query being executed.\n    :type fq: string\n    :param sort: sorting of the search results.  Optional.  Default:\n        ``'relevance asc, metadata_modified desc'``.  As per the solr\n        documentation, this is a comma-separated string of field names and\n        sort-orderings.\n    :type sort: string\n    :param rows: the number of matching rows to return. There is a hard limit\n        of 1000 datasets per query.\n    :type rows: int\n    :param start: the offset in the complete result for where the set of\n        returned datasets should begin.\n    :type start: int\n    :param facet: whether to enable faceted results.  Default: ``True``.\n    :type facet: string\n    :param facet.mincount: the minimum counts for facet fields should be\n        included in the results.\n    :type facet.mincount: int\n    :param facet.limit: the maximum number of values the facet fields return.\n        A negative value means unlimited. This can be set instance-wide with\n        the :ref:`search.facets.limit` config option. Default is 50.\n    :type facet.limit: int\n    :param facet.field: the fields to facet upon.  Default empty.  If empty,\n        then the returned facet information is empty.\n    :type facet.field: list of strings\n    :param include_drafts: if ``True``, draft datasets will be included in the\n        results. A user will only be returned their own draft datasets, and a\n        sysadmin will be returned all draft datasets. Optional, the default is\n        ``False``.\n    :type include_drafts: boolean\n    :param include_private: if ``True``, private datasets will be included in\n        the results. Only private datasets from the user's organizations will\n        be returned and sysadmins will be returned all private datasets.\n        Optional, the default is ``False``.\n    :param use_default_schema: use default package schema instead of\n        a custom schema defined with an IDatasetForm plugin (default: False)\n    :type use_default_schema: bool\n\n\n    The following advanced Solr parameters are supported as well. Note that\n    some of these are only available on particular Solr versions. See Solr's\n    `dismax`_ and `edismax`_ documentation for further details on them:\n\n    ``qf``, ``wt``, ``bf``, ``boost``, ``tie``, ``defType``, ``mm``\n\n\n    .. _dismax: http://wiki.apache.org/solr/DisMaxQParserPlugin\n    .. _edismax: http://wiki.apache.org/solr/ExtendedDisMax\n\n\n    **Examples:**\n\n    ``q=flood`` datasets containing the word `flood`, `floods` or `flooding`\n    ``fq=tags:economy`` datasets with the tag `economy`\n    ``facet.field=[\"tags\"] facet.limit=10 rows=0`` top 10 tags\n\n    **Results:**\n\n    The result of this action is a dict with the following keys:\n\n    :rtype: A dictionary with the following keys\n    :param count: the number of results found.  Note, this is the total number\n        of results found, not the total number of results returned (which is\n        affected by limit and row parameters used in the input).\n    :type count: int\n    :param results: ordered list of datasets matching the query, where the\n        ordering defined by the sort parameter used in the query.\n    :type results: list of dictized datasets.\n    :param facets: DEPRECATED.  Aggregated information about facet counts.\n    :type facets: DEPRECATED dict\n    :param search_facets: aggregated information about facet counts.  The outer\n        dict is keyed by the facet field name (as used in the search query).\n        Each entry of the outer dict is itself a dict, with a \"title\" key, and\n        an \"items\" key.  The \"items\" key's value is a list of dicts, each with\n        \"count\", \"display_name\" and \"name\" entries.  The display_name is a\n        form of the name that can be used in titles.\n    :type search_facets: nested dict of dicts.\n\n    An example result: ::\n\n     {'count': 2,\n      'results': [ { <snip> }, { <snip> }],\n      'search_facets': {u'tags': {'items': [{'count': 1,\n                                             'display_name': u'tolstoy',\n                                             'name': u'tolstoy'},\n                                            {'count': 2,\n                                             'display_name': u'russian',\n                                             'name': u'russian'}\n                                           ]\n                                 }\n                       }\n     }\n\n    **Limitations:**\n\n    The full solr query language is not exposed, including.\n\n    fl\n        The parameter that controls which fields are returned in the solr\n        query cannot be changed.  CKAN always returns the matched datasets as\n        dictionary objects.\n  